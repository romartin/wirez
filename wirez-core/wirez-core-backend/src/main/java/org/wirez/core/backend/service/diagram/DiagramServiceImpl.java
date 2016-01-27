package org.wirez.core.backend.service.diagram;

import org.jboss.errai.bus.server.annotations.Service;
import org.wirez.core.api.DefinitionManager;
import org.wirez.core.api.definition.Definition;
import org.wirez.core.api.diagram.Diagram;
import org.wirez.core.api.diagram.DiagramImpl;
import org.wirez.core.api.diagram.Settings;
import org.wirez.core.api.diagram.SettingsImpl;
import org.wirez.core.api.graph.Graph;
import org.wirez.core.api.registry.DiagramRegistry;
import org.wirez.core.api.service.DefinitionSetServices;
import org.wirez.core.api.service.ResponseStatus;
import org.wirez.core.api.service.ServiceResponse;
import org.wirez.core.api.service.ServiceResponseImpl;
import org.wirez.core.api.service.definition.DefinitionService;
import org.wirez.core.api.service.definition.DefinitionSetServiceResponse;
import org.wirez.core.api.service.diagram.*;
import org.wirez.core.api.util.UUID;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import java.io.InputStream;
import java.util.Collection;
import java.util.LinkedList;

@Service
@ApplicationScoped
public class DiagramServiceImpl implements DiagramService {

    @Inject
    DefinitionManager definitionManager;
    
    @Inject
    DefinitionService definitionService;
    
    @Inject
    DiagramRegistry<Diagram> diagramRegistry;

    @Inject
    Instance<DefinitionSetServices> definitionSetServiceInstances;

    private final Collection<DefinitionSetServices> definitionSetServices = new LinkedList<>();
    
    @PostConstruct
    public void init() {
        for (DefinitionSetServices definitionSetService : definitionSetServiceInstances) {
            definitionSetServices.add(definitionSetService);
        }
    }
    
    @Override
    public DiagramServiceResponse create(DiagramServiceCreateRequest request) {
        final String defSetId = request.getDefinitionSetId();
        final String shapeSetId = request.getShapeSetId();
        final String title = request.getTitle();

        final DefinitionSetServiceResponse definitionSetServiceResponse = definitionService.getDefinitionSet(defSetId);
        final Definition graphDefinition = definitionSetServiceResponse.getGraphElement();
        final String uuid = UUID.uuid();
        final Graph graph = (Graph) definitionService.buildGraphElement(graphDefinition.getId());
        final Settings diagramSettings = new SettingsImpl(title, defSetId, shapeSetId, null);
        final Diagram diagram = new DiagramImpl( uuid, graph, diagramSettings );

        diagramRegistry.add(diagram);

        return new DiagramServiceResponseImpl(ResponseStatus.SUCCESS, diagram);
        
    }

    @Override
    public DiagramServiceResponse load(DiagramServiceLoadRequest request) {

        final String path = request.getPath();

        Diagram diagram = getDiagramInRegistry(path);
        
        // If not cached in the registry, load it.
        if ( null == diagram ) {

            DefinitionSetServices services = getServices( path );
            
            if ( null != services ) {
                
                final DiagramMarshaller marshaller = services.getDiagramMarshaller();
                final InputStream is = getInputStream( path );
                diagram = marshaller.unmarhsall( is );
                
            }
            
        }
        
        if ( null != diagram ) {
            return new DiagramServiceResponseImpl(ResponseStatus.SUCCESS, diagram);
        } else {
            return new DiagramServiceResponseImpl(ResponseStatus.FAIL, null);
        }

    }
    
    protected InputStream getInputStream(final String path) {
        return getClass().getClassLoader().getResourceAsStream(path);
    }

    protected Diagram getDiagramInRegistry(final String path) {
        final Collection<Diagram> diagrams = diagramRegistry.getItems();
        if ( null != diagrams ) {
            for (final Diagram diagram : diagrams) {
                final String _path = diagram.getSettings().getPath();
                
                if ( null != _path && _path.equals(path)) {
                    return diagram;
                }

            }
        }
        
        return null;
        
    }
    
    protected DefinitionSetServices getServices(final String path) {
        for (DefinitionSetServices definitionSetService : definitionSetServices) {
            if (definitionSetService.accepts( path )) {
                return definitionSetService;
            }
        }
        
        return null;
    }

    // TODO
    @Override
    public ServiceResponse save(DiagramServiceSaveRequest request) {

        final Diagram diagram = request.getDiagram();

        if (diagramRegistry.contains(diagram)) {
            diagramRegistry.update(diagram);
        } else {
            diagramRegistry.add(diagram);
        }
        
        return new ServiceResponseImpl(ResponseStatus.SUCCESS);
    }

    // TODO
    @Override
    public ServiceResponse delete(DiagramServiceDeleteRequest request) {

        final String uuid = request.getUUID();

        final Diagram diagram = diagramRegistry.get(uuid);
        if (null != diagram) {
            diagramRegistry.remove(diagram);
            return new ServiceResponseImpl(ResponseStatus.SUCCESS);
        }

        return new ServiceResponseImpl(ResponseStatus.FAIL);
    }
    
}
