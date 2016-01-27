package org.wirez.core.backend.service.diagram;

import org.jboss.errai.bus.server.annotations.Service;
import org.wirez.core.api.DefinitionManager;
import org.wirez.core.api.adapter.DefinitionSetAdapter;
import org.wirez.core.api.adapter.DefinitionSetRuleAdapter;
import org.wirez.core.api.definition.Definition;
import org.wirez.core.api.definition.DefinitionSet;
import org.wirez.core.api.definition.property.PropertySet;
import org.wirez.core.api.diagram.Diagram;
import org.wirez.core.api.diagram.DiagramImpl;
import org.wirez.core.api.diagram.Settings;
import org.wirez.core.api.diagram.SettingsImpl;
import org.wirez.core.api.graph.Element;
import org.wirez.core.api.graph.Graph;
import org.wirez.core.api.graph.impl.DefaultGraph;
import org.wirez.core.api.registry.DiagramRegistry;
import org.wirez.core.api.rule.Rule;
import org.wirez.core.api.service.definition.DefinitionService;
import org.wirez.core.api.service.definition.DefinitionSetServiceResponse;
import org.wirez.core.api.service.diagram.DiagramServiceResponse;
import org.wirez.core.api.service.diagram.DiagramService;
import org.wirez.core.api.service.diagram.DiagramServiceResponseImpl;
import org.wirez.core.api.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Collection;
import java.util.Set;

@Service
@ApplicationScoped
public class DiagramServiceImpl implements DiagramService {

    @Inject
    DefinitionManager definitionManager;
    
    @Inject
    DefinitionService definitionService;
    
    @Inject
    DiagramRegistry<Diagram> diagramRegistry;


    @Override
    public DiagramServiceResponse create(final String defSetId, 
                                         final String shapeSetId, 
                                         final String title) {
        
        final DefinitionSetServiceResponse definitionSetServiceResponse = definitionService.getDefinitionSet(defSetId);
        final Definition graphDefinition = definitionSetServiceResponse.getGraphElement();
        final String uuid = UUID.uuid();
        final Graph graph = (Graph) definitionService.buildGraphElement(graphDefinition.getId());
        final Settings diagramSettings = new SettingsImpl(title, defSetId, shapeSetId);
        final Diagram diagram = new DiagramImpl( uuid, graph, diagramSettings );
        
        diagramRegistry.add(diagram);
        
        return new DiagramServiceResponseImpl(diagram);
    }

    @Override
    public DiagramServiceResponse load(final String uuid) {
        assert uuid != null;
        final Diagram diagram = (Diagram) diagramRegistry.get(uuid);
        return new DiagramServiceResponseImpl(diagram);
    }

    @Override
    public void save(final Diagram diagram) {
        assert diagram != null;
        
        if (diagramRegistry.contains(diagram)) {
            diagramRegistry.update(diagram);
        } else {
            diagramRegistry.add(diagram);
        }
        
    }

    @Override
    public Diagram delete(final String uuid) {
        assert uuid != null;
        final Diagram diagram = (Diagram) diagramRegistry.get(uuid);
        if ( null != diagram ) {
            diagramRegistry.remove(diagram);
            return diagram;
        }
        
        throw new RuntimeException("Diagram cannot be removed as it does not exist.");
    }
}
