package org.wirez.bpmn.backend.marshall.json;

import bpsim.impl.BpsimPackageImpl;
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
import org.jboss.drools.impl.DroolsPackageImpl;
import org.jboss.drools.util.DroolsResourceFactoryImpl;
import org.wirez.bpmn.backend.legacy.Bpmn2JsonMarshaller;
import org.wirez.bpmn.backend.marshall.json.builder.BPMNGraphGenerator;
import org.wirez.bpmn.backend.marshall.json.builder.BPMNGraphObjectBuilderFactory;
import org.wirez.core.api.graph.impl.DefaultGraph;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Bpmn2UnMarshaller extends Bpmn2JsonMarshaller {

    final static ResourceSet resourceSet = new ResourceSetImpl();
    static {
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
    
    BPMNGraphObjectBuilderFactory wiresFactory;
    BPMNGraphGenerator bpmnGraphGenerator;

    public Bpmn2UnMarshaller(BPMNGraphObjectBuilderFactory wiresFactory) {
        this.wiresFactory = wiresFactory;
        this.bpmnGraphGenerator = new BPMNGraphGenerator(wiresFactory);
    }

    public Collection<DefaultGraph> unmarshall(String content) throws IOException {
        XMLResource outResource = (XMLResource) resourceSet.createResource( URI.createURI( "inputStream://dummyUriWithValidSuffix.xml" ) );
        outResource.getDefaultLoadOptions().put( XMLResource.OPTION_ENCODING, "UTF-8" );
        outResource.setEncoding( "UTF-8" );
        Map<String, Object> options = new HashMap<String, Object>();
        options.put( XMLResource.OPTION_ENCODING, "UTF-8" );
        outResource.load(new BufferedInputStream(new ByteArrayInputStream(content.getBytes("UTF-8"))), options );
        DocumentRoot root = (DocumentRoot) outResource.getContents().get( 0 );
        Definitions definitions = root.getDefinitions();
        return unmarshall(definitions, null);
    }

    public Collection<DefaultGraph> unmarshall(Definitions def, String preProcessingData) throws IOException {
        DroolsPackageImpl.init();
        BpsimPackageImpl.init();
        super.marshall(bpmnGraphGenerator, def, preProcessingData);
        return bpmnGraphGenerator.getGraphs();
    }
    
}
