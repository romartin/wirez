package org.wirez.bpmn.backend.service.diagram;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.wirez.bpmn.api.factory.BPMNDefinitionFactory;
import org.wirez.bpmn.api.factory.BPMNDefinitionSetFactory;
import org.wirez.bpmn.api.factory.BPMNPropertyFactory;
import org.wirez.bpmn.api.factory.BPMNPropertySetFactory;
import org.wirez.bpmn.api.property.general.Name;
import org.wirez.bpmn.backend.factory.BPMNGraphFactory;
import org.wirez.bpmn.backend.marshall.json.builder.BPMNGraphObjectBuilderFactory;
import org.wirez.bpmn.backend.marshall.json.oryx.Bpmn2OryxIdMappings;
import org.wirez.bpmn.backend.marshall.json.oryx.Bpmn2OryxManager;
import org.wirez.bpmn.backend.marshall.json.oryx.property.*;
import org.wirez.core.api.DefinitionManager;
import org.wirez.core.api.definition.adapter.shared.HasValuePropertyAdapter;
import org.wirez.core.api.definition.factory.ModelFactory;
import org.wirez.core.api.definition.util.DefinitionUtils;
import org.wirez.core.api.diagram.Diagram;
import org.wirez.core.api.diagram.DiagramImpl;
import org.wirez.core.api.diagram.Settings;
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Element;
import org.wirez.core.api.graph.Graph;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.command.GraphCommandManager;
import org.wirez.core.api.graph.command.GraphCommandManagerImpl;
import org.wirez.core.api.graph.command.factory.GraphCommandFactory;
import org.wirez.core.api.graph.command.factory.GraphCommandFactoryImpl;
import org.wirez.core.api.graph.factory.ConnectionEdgeFactory;
import org.wirez.core.api.graph.factory.ConnectionEdgeFactoryImpl;
import org.wirez.core.api.graph.factory.ViewNodeFactory;
import org.wirez.core.api.graph.factory.ViewNodeFactoryImpl;
import org.wirez.core.api.graph.util.GraphUtils;
import org.wirez.core.api.rule.impl.empty.EmptyRulesManagerImpl;
import org.wirez.core.backend.ApplicationFactoryManager;
import org.wirez.core.backend.definition.adapter.AnnotatedDefinitionAdapter;
import org.wirez.core.backend.definition.adapter.AnnotatedDefinitionSetAdapter;
import org.wirez.core.backend.definition.adapter.AnnotatedPropertyAdapter;
import org.wirez.core.backend.definition.adapter.AnnotatedPropertySetAdapter;

import javax.enterprise.inject.spi.BeanManager;
import java.io.InputStream;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

// TODO: Improve assertions.
@RunWith(MockitoJUnitRunner.class)
public class BPMNDiagramMarshallerTest {

    protected static final String BPMN_BASIC = "org/wirez/bpmn/backend/service/diagram/basic.bpmn";
    protected static final String BPMN_EVALUATION = "org/wirez/bpmn/backend/service/diagram/evaluation.bpmn";
    protected static final String BPMN_LANES = "org/wirez/bpmn/backend/service/diagram/lanes.bpmn";

    @Mock
    DefinitionManager definitionManager;

    @Mock
    BeanManager beanManager;
    
    @Mock
    ApplicationFactoryManager applicationFactoryManager;
    
    ConnectionEdgeFactory<Object> connectionEdgeFactory;
    ViewNodeFactory<Object> viewNodeFactory;
    DefinitionUtils definitionUtils;
    GraphUtils graphUtils;
    
    GraphCommandManager commandManager;
    GraphCommandFactory commandFactory;

    BPMNGraphFactory bpmnGraphFactory;
    BPMNPropertyFactory bpmnPropertyBuilder;
    BPMNPropertySetFactory bpmnPropertySetBuilder;
    BPMNDefinitionSetFactory bpmnDefinitionSetFactory;
    BPMNDefinitionFactory bpmnDefinitionFactory;

    Bpmn2OryxIdMappings oryxIdMappings;
    Bpmn2OryxPropertyManager oryxPropertyManager;
    Bpmn2OryxManager oryxManager;

    BPMNGraphObjectBuilderFactory objectBuilderFactory;
    
    private BPMNDiagramMarshaller tested;
    
    @Before
    public void setup() throws Exception {
        
        // Graph utils.
        definitionUtils = new DefinitionUtils( definitionManager );
        graphUtils = new GraphUtils( definitionManager, definitionUtils );
        
        // Definition manager.        
        final AnnotatedDefinitionAdapter definitionAdapter = new AnnotatedDefinitionAdapter( graphUtils );
        final AnnotatedDefinitionSetAdapter definitionSetAdapter = new AnnotatedDefinitionSetAdapter( definitionAdapter );
        final AnnotatedPropertySetAdapter propertySetAdapter = new AnnotatedPropertySetAdapter();
        final AnnotatedPropertyAdapter propertyAdapter = new AnnotatedPropertyAdapter();
        when(definitionManager.getDefinitionSetAdapter( any(Class.class) )).thenReturn( definitionSetAdapter );
        when(definitionManager.getDefinitionAdapter( any(Class.class) )).thenReturn( definitionAdapter );
        when(definitionManager.getPropertySetAdapter( any(Class.class) )).thenReturn( propertySetAdapter );
        
        doAnswer(invocationOnMock -> {
            Class<?> c = (Class<?>) invocationOnMock.getArguments()[0];
            // Name is not a pojo annotated property.
            if ( c.equals(Name.class) ) {
                return new HasValuePropertyAdapter();
            }
            return propertyAdapter;
        }).when(definitionManager).getPropertyAdapter( any(Class.class) );
        
        // BPMN Factories.
        bpmnPropertyBuilder = new BPMNPropertyFactory();
        bpmnPropertySetBuilder = new BPMNPropertySetFactory( bpmnPropertyBuilder );
        bpmnDefinitionFactory = new BPMNDefinitionFactory( bpmnPropertyBuilder, bpmnPropertySetBuilder );
        bpmnDefinitionSetFactory = new BPMNDefinitionSetFactory( bpmnDefinitionFactory );

        List<ModelFactory<?>> modelFactories = new LinkedList<>();
        modelFactories.add( bpmnPropertyBuilder );
        modelFactories.add( bpmnPropertySetBuilder );
        modelFactories.add( bpmnDefinitionFactory );
        modelFactories.add( bpmnDefinitionSetFactory );

        commandManager = new GraphCommandManagerImpl( null, null );
        commandFactory = new GraphCommandFactoryImpl();
        connectionEdgeFactory = new ConnectionEdgeFactoryImpl();
        viewNodeFactory = new ViewNodeFactoryImpl();
        
        bpmnGraphFactory = new BPMNGraphFactory( definitionManager, applicationFactoryManager, bpmnDefinitionFactory, 
                commandManager, commandFactory, EmptyRulesManagerImpl.INSTANCE);
        
        doAnswer(invocationOnMock -> {
            String id = (String) invocationOnMock.getArguments()[0];
            for ( ModelFactory<?> modelFactory : modelFactories ) {
                if ( modelFactory.accepts( id ) ) {
                    return modelFactory.build( id );
                }
            }
           
            return null;
        }).when( applicationFactoryManager ).newDomainObject( anyString() );

        doAnswer(invocationOnMock -> {
            String uuid = (String) invocationOnMock.getArguments()[0];
            String id = (String) invocationOnMock.getArguments()[1];
            Object model = null;
            for ( ModelFactory<?> modelFactory : modelFactories ) {
                if ( modelFactory.accepts( id ) ) {
                    model = modelFactory.build( id );
                    break;
                }
            }
            
            if ( null != model ) {
                Class<? extends Element> element = AnnotatedDefinitionAdapter.getGraphElement( model.getClass() );
                if ( element.isAssignableFrom(Node.class) ) {
                    return viewNodeFactory.build( uuid, model, new HashSet<>() );
                } else if ( element.isAssignableFrom(Edge.class) ) {
                    return connectionEdgeFactory.build( uuid, model, new HashSet<>() );
                }
            }

            return null;
        }).when( applicationFactoryManager ).newElement( anyString(), anyString() );

        doAnswer(invocationOnMock -> {
            String uuid = (String) invocationOnMock.getArguments()[0];
            String defSetId = (String) invocationOnMock.getArguments()[1];
            return bpmnGraphFactory.build( uuid, defSetId, new HashSet<>() );            
        }).when( applicationFactoryManager ).newGraph( anyString(), anyString() );

        doAnswer(invocationOnMock -> {
            String uuid = (String) invocationOnMock.getArguments()[0];
            Graph graph = (Graph) invocationOnMock.getArguments()[1];
            Settings settings = (Settings) invocationOnMock.getArguments()[2];
            return new DiagramImpl( uuid, graph, settings );
        }).when( applicationFactoryManager ).newDiagram( anyString(), any(Graph.class), any(Settings.class) );
        
        // Bpmn 2 oryx stuff.
        oryxIdMappings = new Bpmn2OryxIdMappings();
        StringTypeSerializer stringTypeSerializer = new StringTypeSerializer();
        BooleanTypeSerializer booleanTypeSerializer = new BooleanTypeSerializer();
        ColorTypeSerializer colorTypeSerializer = new ColorTypeSerializer();
        DoubleTypeSerializer doubleTypeSerializer = new DoubleTypeSerializer();
        IntegerTypeSerializer integerTypeSerializer = new IntegerTypeSerializer();
        List<Bpmn2OryxPropertySerializer<?>> propertySerializers = new LinkedList<>();
        propertySerializers.add( stringTypeSerializer );
        propertySerializers.add( booleanTypeSerializer );
        propertySerializers.add( colorTypeSerializer );
        propertySerializers.add( doubleTypeSerializer );
        propertySerializers.add( integerTypeSerializer );
        oryxPropertyManager = new Bpmn2OryxPropertyManager( propertySerializers );
        oryxManager = new Bpmn2OryxManager( bpmnDefinitionFactory, bpmnPropertyBuilder, oryxIdMappings, 
                oryxPropertyManager );
        oryxManager.init();
        
        // Marshalling factories.
        objectBuilderFactory = new BPMNGraphObjectBuilderFactory( definitionManager, oryxManager );
        
        // The tested BPMN marshaller.
        tested = new BPMNDiagramMarshaller( objectBuilderFactory, definitionManager, graphUtils, 
                oryxManager, applicationFactoryManager, commandManager, EmptyRulesManagerImpl.INSTANCE, commandFactory);
    }

    // 4 nodes expected: BPMNDiagram, StartNode, Task and EndNode
    @Test
    public void testUnmarshallBasic() {
        Diagram<Graph, Settings> diagram = unmarshall(BPMN_BASIC);
        assertDiagram( diagram, 4 );
        assertEquals( "Basic process", diagram.getSettings().getTitle() );
    }
    
    @Test
    public void testUmarshallEvaluation() {
        Diagram<Graph, Settings> diagram = unmarshall(BPMN_EVALUATION);
        assertDiagram( diagram, 8 );
        assertEquals( "Evaluation", diagram.getSettings().getTitle() );
    }

    @Test
    public void testUnmarshallSeveralDiagrams() {
        Diagram<Graph, Settings> diagram1 = unmarshall(BPMN_EVALUATION);
        assertDiagram( diagram1, 8 );
        assertEquals( "Evaluation", diagram1.getSettings().getTitle() );
        Diagram<Graph, Settings> diagram = unmarshall(BPMN_LANES);
        assertDiagram( diagram, 7 );
        assertEquals( "Lanes test", diagram.getSettings().getTitle() );
    }

    @Test
    public void testMarshallBasic() {
        Diagram<Graph, Settings> diagram = unmarshall(BPMN_BASIC);
        String result = tested.marshall(diagram);
        assertDiagram( result, 1, 3, 2);
    }
    
    @Test
    public void testMarshallEvaluation() {
        Diagram<Graph, Settings> diagram = unmarshall(BPMN_EVALUATION);
        String result = tested.marshall(diagram);
        assertDiagram( result, 1, 7, 7);
    }

    private void assertDiagram(String result, int diagramCount, int nodeCount, int edgeCount) {
        int d = count( result, "<bpmndi:BPMNDiagram" );
        int n = count( result, "<bpmndi:BPMNShape" );
        int e = count( result, "<bpmndi:BPMNEdge" );
        assertEquals( diagramCount, d );
        assertEquals( nodeCount, n );
        assertEquals( edgeCount, e );
    }
    
    private void assertDiagram(Diagram<Graph, Settings> diagram, int nodesSize) {
        Graph graph = diagram.getGraph();
        assertNotNull(graph);
        Iterator<Node> nodesIterable = graph.nodes().iterator();

        List<Node> nodes = new ArrayList<>();
        nodesIterable.forEachRemaining(nodes::add);
        assertEquals(nodesSize, nodes.size());
    }
    
    private Diagram<Graph, Settings> unmarshall(String fileName) {
        InputStream is = loadStream(fileName);
        return tested.unmarhsall(is);
    }

    protected InputStream loadStream(String path) {
        return Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
    }

    protected static int count(final String string, final String substring)
    {
        int count = 0;
        int idx = 0;
        while ((idx = string.indexOf(substring, idx)) != -1) {
            idx++;
            count++;
        }

        return count;
    }

}
