package org.wirez.bpmn.backend.service.diagram;

import org.eclipse.bpmn2.Bpmn2Package;
import org.eclipse.bpmn2.Definitions;
import org.eclipse.bpmn2.DocumentRoot;
import org.eclipse.bpmn2.util.Bpmn2ResourceFactoryImpl;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.jboss.drools.DroolsPackage;
import org.jboss.drools.util.DroolsResourceFactoryImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wirez.bpmn.BPMNDefinitionSet;
import org.wirez.bpmn.definition.BPMNDiagram;
import org.wirez.bpmn.backend.legacy.profile.impl.DefaultProfileImpl;
import org.wirez.bpmn.backend.marshall.json.Bpmn2Marshaller;
import org.wirez.bpmn.backend.marshall.json.Bpmn2UnMarshaller;
import org.wirez.bpmn.backend.marshall.json.builder.BPMNGraphObjectBuilderFactory;
import org.wirez.bpmn.backend.marshall.json.oryx.Bpmn2OryxManager;
import org.wirez.core.api.DefinitionManager;
import org.wirez.core.api.FactoryManager;
import org.wirez.core.definition.adapter.binding.BindableAdapterUtils;
import org.wirez.core.diagram.Diagram;
import org.wirez.core.diagram.DiagramImpl;
import org.wirez.core.diagram.Settings;
import org.wirez.core.diagram.SettingsImpl;
import org.wirez.core.diagram.marshall.DiagramMarshaller;
import org.wirez.core.graph.Graph;
import org.wirez.core.graph.Node;
import org.wirez.core.graph.command.GraphCommandManager;
import org.wirez.core.graph.command.factory.GraphCommandFactory;
import org.wirez.core.graph.content.definition.Definition;
import org.wirez.core.graph.util.GraphUtils;
import org.wirez.core.util.UUID;
import org.wirez.core.backend.annotation.Application;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Dependent
public class BPMNDiagramMarshaller implements DiagramMarshaller<Diagram, InputStream, String> {

    public static final String TEST_PATH = "org/wirez/bpmn/backend/examples/wirez-bpmn-test.bpmn2";
    private static final Logger LOG = LoggerFactory.getLogger(BPMNDiagramMarshaller.class);

    BPMNGraphObjectBuilderFactory bpmnGraphBuilderFactory;
    DefinitionManager definitionManager;
    GraphUtils graphUtils;
    Bpmn2OryxManager oryxManager;
    FactoryManager factoryManager;
    GraphCommandManager graphCommandManager;
    GraphCommandFactory commandFactory;
    
    private ResourceSet resourceSet;

    @Inject
    public BPMNDiagramMarshaller(BPMNGraphObjectBuilderFactory bpmnGraphBuilderFactory, 
                                 DefinitionManager definitionManager,
                                 GraphUtils graphUtils,
                                 Bpmn2OryxManager oryxManager,
                                 @Application FactoryManager factoryManager, 
                                 GraphCommandManager graphCommandManager,
                                 GraphCommandFactory commandFactory) {
        this.bpmnGraphBuilderFactory = bpmnGraphBuilderFactory;
        this.definitionManager = definitionManager;
        this.graphUtils = graphUtils;
        this.oryxManager = oryxManager;
        this.factoryManager = factoryManager;
        this.graphCommandManager = graphCommandManager;
        this.commandFactory = commandFactory;
    }

    private void initResourceSet() {

        resourceSet = new ResourceSetImpl();

        resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put
                ( Resource.Factory.Registry.DEFAULT_EXTENSION,
                        new DroolsResourceFactoryImpl() );
        resourceSet.getPackageRegistry().put
                ( DroolsPackage.eNS_URI,
                        DroolsPackage.eINSTANCE );
        resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap()
                .put( Resource.Factory.Registry.DEFAULT_EXTENSION, new Bpmn2ResourceFactoryImpl() );
        resourceSet.getPackageRegistry().put( "http://www.omg.org/spec/BPMN/20100524/MODEL", Bpmn2Package.eINSTANCE );
        
    }

    @Override
    public String marshall( Diagram diagram ) throws IOException {

        LOG.info("Starting BPMN diagram marshalling...");

        Bpmn2Marshaller marshaller = new Bpmn2Marshaller( definitionManager, graphUtils, oryxManager );

        String result = null;
        try {
            result = marshaller.marshall(diagram);
            LOG.debug("Marhall result=" + result);
        } catch (IOException e) {
            LOG.error("Error marshalling bpmn file.", e);
        }

        LOG.info("BPMN diagram marshalling finished successfully.");
        
        return result;
    }
    
    @Override
    public Diagram unmarhsall( InputStream inputStream ) throws IOException {

        LOG.info("Starting BPMN diagram loading...");
        
        try {
            
            Definitions definitions = parseDefinitions(inputStream);
            // No rule checking for marshalling/unmarshalling, current jbpm designer marshallers should do it for us.
            Bpmn2UnMarshaller parser = new Bpmn2UnMarshaller(bpmnGraphBuilderFactory, 
                    definitionManager,
                    factoryManager,
                    graphUtils,
                    oryxManager,
                    graphCommandManager,
                    commandFactory);
            
            parser.setProfile( new DefaultProfileImpl() );
            final Graph graph = parser.unmarshall( definitions, null );
            final Node diagramNode = getFirstDiagramNode( graph );
            final BPMNDiagram diagram = null != diagramNode ?
                    (BPMNDiagram) ( (Definition) diagramNode.getContent()).getDefinition() : null;
            final String rootUUID = null != diagramNode ? diagramNode.getUUID() : null;
            final String title = getTitle( diagram );

            final String defSetId = BindableAdapterUtils.getDefinitionSetId( BPMNDefinitionSet.class );
            final Diagram<Graph, Settings> result = new DiagramImpl( UUID.uuid(), graph,
                    new SettingsImpl( title, defSetId,
                            BindableAdapterUtils.getShapeSetId( BPMNDefinitionSet.class ), rootUUID ));

            LOG.info("BPMN diagram loading finished successfully.");

            return result;
            
        } catch (IOException e) {
            LOG.error("Error parsing bpmn file.", e);
        }
        
        return null;
    }

    protected Definitions parseDefinitions(final InputStream inputStream) throws IOException {
        try {
            initResourceSet();
            XMLResource outResource = (XMLResource) resourceSet.createResource( URI.createURI( "inputStream://dummyUriWithValidSuffix.xml" ) );
            outResource.getDefaultLoadOptions().put( XMLResource.OPTION_ENCODING, "UTF-8" );
            outResource.setEncoding( "UTF-8" );
            Map<String, Object> options = new HashMap<String, Object>();
            options.put( XMLResource.OPTION_ENCODING, "UTF-8" );
            outResource.load( inputStream, options );

            DocumentRoot root = (DocumentRoot) outResource.getContents().get( 0 );
            return root.getDefinitions();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return null;
    }
    
    @SuppressWarnings("unchecked")
    protected Node getFirstDiagramNode( final Graph graph ) {

        if ( null != graph ) {
            Iterable<Node> nodesIterable = graph.nodes();
            if ( null != nodesIterable ) {
                Iterator<Node> nodesIt = nodesIterable.iterator();
                if ( null != nodesIt ) {
                    while ( nodesIt.hasNext() ) {
                        Node node = nodesIt.next();
                        Object content = node.getContent();
                        if ( content instanceof Definition ) {
                            Definition definitionContent = (Definition) content;
                            if (  definitionContent.getDefinition() instanceof BPMNDiagram ) {
                                return node;
                            }
                        }
                        
                    }
                }
            }
            
        }
        
        return null;
    }

    protected String getTitle( BPMNDiagram diagram ) {
        final String title = diagram.getGeneral().getName().getValue();
        return title != null && title.trim().length() > 0 ? title : "-- Untitled BPMN2 diagram --";
    }
}
