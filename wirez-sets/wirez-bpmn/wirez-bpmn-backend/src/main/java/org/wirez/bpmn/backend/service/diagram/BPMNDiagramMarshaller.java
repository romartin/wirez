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
import org.wirez.bpmn.api.BPMNDefinitionSet;
import org.wirez.bpmn.api.BPMNDiagram;
import org.wirez.bpmn.backend.legacy.profile.impl.DefaultProfileImpl;
import org.wirez.bpmn.backend.marshall.json.Bpmn2UnMarshaller;
import org.wirez.bpmn.backend.marshall.json.builder.BPMNGraphObjectBuilderFactory;
import org.wirez.core.api.definition.Definition;
import org.wirez.core.api.diagram.Diagram;
import org.wirez.core.api.diagram.DiagramImpl;
import org.wirez.core.api.diagram.Settings;
import org.wirez.core.api.diagram.SettingsImpl;
import org.wirez.core.api.graph.Graph;
import org.wirez.core.api.graph.content.ViewContent;
import org.wirez.core.api.graph.impl.DefaultGraph;
import org.wirez.core.api.service.diagram.DiagramMarshaller;
import org.wirez.core.api.util.UUID;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Dependent
public class BPMNDiagramMarshaller implements DiagramMarshaller<InputStream, Settings> {

    public static final String TEST_PATH = "org/wirez/bpmn/backend/examples/wirez-bpmn-test.bpmn2";
    private static final Logger LOG = LoggerFactory.getLogger(BPMNDiagramMarshaller.class);

    @Inject
    BPMNGraphObjectBuilderFactory bpmnGraphBuilderFactory;
    
    private ResourceSet resourceSet;
    
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

    // TODO
    @Override
    public String marshall(Diagram<Settings> diagram) {
        return null;
    }
    
    @Override
    public Diagram<Settings> unmarhsall(InputStream inputStream) {

        LOG.info("Starting BPMN process loading...");
        
        try {
            
            Definitions definitions = parseDefinitions(inputStream);
            Bpmn2UnMarshaller parser = new Bpmn2UnMarshaller(bpmnGraphBuilderFactory);
            parser.setProfile(new DefaultProfileImpl());
            Collection<DefaultGraph> bpmnGraphs = parser.unmarshall(definitions, null);
            final String uuid = UUID.uuid();
            final Graph graph = bpmnGraphs.iterator().next();
            final BPMNDiagram graphDefinition = (BPMNDiagram) ( (ViewContent) graph.getContent() ).getDefinition();
            String title = graphDefinition.getGeneral().getName().getValue();
            
            if ( title == null || title.trim().length() == 0 ) {
                title = "Untitled BPMN diagram";
            }
            
            final Diagram<Settings> diagram = new DiagramImpl( uuid, graph, 
                    new SettingsImpl(title, BPMNDefinitionSet.ID, "BPMNShapeSet"));

            LOG.info("BPMN process loading finished successfully.");

            return diagram;
            
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
}
