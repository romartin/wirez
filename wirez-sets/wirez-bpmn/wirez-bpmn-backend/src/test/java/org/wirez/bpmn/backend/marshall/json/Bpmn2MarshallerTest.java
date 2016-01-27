package org.wirez.bpmn.backend.marshall.json;

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
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.wirez.bpmn.backend.legacy.profile.impl.DefaultProfileImpl;
import org.wirez.bpmn.backend.marshall.json.builder.BPMNGraphObjectBuilderFactory;
import org.wirez.core.api.graph.impl.DefaultGraph;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.spy;

@Ignore
@RunWith(MockitoJUnitRunner.class)
public class Bpmn2MarshallerTest {

    BPMNGraphObjectBuilderFactory bpmnWiresFactory;
    private ResourceSet resourceSet;
    
    @Before
    public void setup() {
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
        
        bpmnWiresFactory = spy(new BPMNGraphObjectBuilderFactory());
    }

    @Test
    public void parseGraphFile() throws IOException {
        Definitions definitions = parseDefinitions();
        Bpmn2UnMarshaller parser = new Bpmn2UnMarshaller(bpmnWiresFactory);
        parser.setProfile(new DefaultProfileImpl());
        Collection<DefaultGraph> bpmnGraphs = parser.unmarshall(definitions, null);
        log(bpmnGraphs);
        log("BPMN2 load test finished!");
    }

    protected Definitions parseDefinitions() throws IOException {
        InputStream inputStream = null;
        try {
            log("Starting BPMN2 load test...");
            XMLResource outResource = (XMLResource) resourceSet.createResource( URI.createURI( "inputStream://dummyUriWithValidSuffix.xml" ) );
            inputStream = Bpmn2MarshallerTest.class.getClassLoader().getResourceAsStream( "test.bpmn2" );
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

    // @Test
    public void parseJSONFile() throws IOException {
        /*Definitions definitions = parseDefinitions();
        Bpmn2JsonMarshaller parser = new Bpmn2JsonMarshaller();
        parser.setProfile(new DefaultProfileImpl());
        String json = parser.marshall(definitions, null);
        logger.log(json);
        logger.log("BPMN2 load test finished!");*/
    }
    
    private void log(String message) {
        System.out.println(message);
    }

    private void log(Collection<DefaultGraph> graphs) {
        // TODO
    }
}