package org.wirez.bpmn.backend.service.diagram;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.wirez.backend.ApplicationFactoryManager;
import org.wirez.backend.definition.factory.TestScopeModelFactory;
import org.wirez.bpmn.BPMNDefinitionSet;
import org.wirez.bpmn.backend.factory.BPMNGraphFactory;
import org.wirez.bpmn.backend.marshall.json.builder.BPMNGraphObjectBuilderFactory;
import org.wirez.bpmn.backend.marshall.json.oryx.Bpmn2OryxIdMappings;
import org.wirez.bpmn.backend.marshall.json.oryx.Bpmn2OryxManager;
import org.wirez.bpmn.backend.marshall.json.oryx.property.*;
import org.wirez.bpmn.definition.adapter.morph.TaskMorphAdapter;
import org.wirez.bpmn.definition.NoneTask;
import org.wirez.bpmn.definition.UserTask;
import org.wirez.core.api.DefinitionManager;
import org.wirez.core.backend.definition.adapter.annotation.RuntimeDefinitionAdapter;
import org.wirez.core.backend.definition.adapter.annotation.RuntimeDefinitionSetAdapter;
import org.wirez.core.backend.definition.adapter.annotation.RuntimePropertyAdapter;
import org.wirez.core.backend.definition.adapter.annotation.RuntimePropertySetAdapter;
import org.wirez.core.backend.definition.adapter.binding.RuntimeBindableMorphAdapterFactory;
import org.wirez.core.definition.util.DefinitionUtils;
import org.wirez.core.diagram.Diagram;
import org.wirez.core.diagram.DiagramImpl;
import org.wirez.core.diagram.Settings;
import org.wirez.core.graph.Edge;
import org.wirez.core.graph.Element;
import org.wirez.core.graph.Graph;
import org.wirez.core.graph.Node;
import org.wirez.core.graph.command.GraphCommandManager;
import org.wirez.core.graph.command.GraphCommandManagerImpl;
import org.wirez.core.graph.command.factory.GraphCommandFactory;
import org.wirez.core.graph.command.factory.GraphCommandFactoryImpl;
import org.wirez.core.graph.content.definition.Definition;
import org.wirez.core.graph.content.relationship.Dock;
import org.wirez.core.graph.content.view.Bounds;
import org.wirez.core.graph.content.view.View;
import org.wirez.core.graph.content.view.ViewConnector;
import org.wirez.core.graph.factory.ConnectionEdgeFactory;
import org.wirez.core.graph.factory.ConnectionEdgeFactoryImpl;
import org.wirez.core.graph.factory.ViewNodeFactory;
import org.wirez.core.graph.factory.ViewNodeFactoryImpl;
import org.wirez.core.graph.util.GraphUtils;

import javax.enterprise.inject.spi.BeanManager;
import java.io.InputStream;
import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

// TODO: Use Archillian to avoid all that CDI mockings. 
@RunWith(MockitoJUnitRunner.class)
public class BPMNDiagramMarshallerTest {

    protected static final String BPMN_BASIC = "org/wirez/bpmn/backend/service/diagram/basic.bpmn";
    protected static final String BPMN_EVALUATION = "org/wirez/bpmn/backend/service/diagram/evaluation.bpmn";
    protected static final String BPMN_LANES = "org/wirez/bpmn/backend/service/diagram/lanes.bpmn";
    protected static final String BPMN_BOUNDARY_EVENTS = "org/wirez/bpmn/backend/service/diagram/boundaryIntmEvent.bpmn";
    protected static final String BPMN_NOT_BOUNDARY_EVENTS = "org/wirez/bpmn/backend/service/diagram/notBoundaryIntmEvent.bpmn";
    
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

    Bpmn2OryxIdMappings oryxIdMappings;
    Bpmn2OryxPropertyManager oryxPropertyManager;
    Bpmn2OryxManager oryxManager;

    TestScopeModelFactory testScopeModelFactory;
    BPMNGraphObjectBuilderFactory objectBuilderFactory;

    TaskMorphAdapter taskMorphAdapter;
    
    private BPMNDiagramMarshaller tested;
    
    @Before
    public void setup() throws Exception {
        
        // Graph utils.
        definitionUtils = new DefinitionUtils( definitionManager );
        graphUtils = new GraphUtils( definitionManager, definitionUtils );

        testScopeModelFactory = new TestScopeModelFactory( new BPMNDefinitionSet.BPMNDefinitionSetBuilder().build() );
        
        // Definition manager.        
        final RuntimeDefinitionAdapter definitionAdapter = new RuntimeDefinitionAdapter( definitionUtils );
        final RuntimeDefinitionSetAdapter definitionSetAdapter = new RuntimeDefinitionSetAdapter( definitionAdapter );
        final RuntimePropertySetAdapter propertySetAdapter = new RuntimePropertySetAdapter();
        final RuntimePropertyAdapter propertyAdapter = new RuntimePropertyAdapter();
        when(definitionManager.getDefinitionSetAdapter( any(Class.class) )).thenReturn( definitionSetAdapter );
        when(definitionManager.getDefinitionAdapter( any(Class.class) )).thenReturn( definitionAdapter );
        when(definitionManager.getPropertySetAdapter( any(Class.class) )).thenReturn( propertySetAdapter );
        when(definitionManager.getPropertyAdapter( any(Class.class) )).thenReturn( propertyAdapter );

        commandManager = new GraphCommandManagerImpl( null, null, null );
        commandFactory = new GraphCommandFactoryImpl();
        connectionEdgeFactory = new ConnectionEdgeFactoryImpl();
        viewNodeFactory = new ViewNodeFactoryImpl();
        
        bpmnGraphFactory = new BPMNGraphFactory( definitionManager, applicationFactoryManager,
                commandManager, commandFactory );
        
        doAnswer(invocationOnMock -> {
            String id = (String) invocationOnMock.getArguments()[0];
            return testScopeModelFactory.build( id );
        }).when( applicationFactoryManager ).newDomainObject( anyString() );

        doAnswer(invocationOnMock -> {
            String uuid = (String) invocationOnMock.getArguments()[0];
            String id = (String) invocationOnMock.getArguments()[1];
            Object model = testScopeModelFactory.accepts( id ) ? testScopeModelFactory.build( id ) : null;
            
            if ( null != model ) {
                Class<? extends Element> element = RuntimeDefinitionAdapter.getGraphElement( model.getClass() );
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
        oryxIdMappings = new Bpmn2OryxIdMappings( definitionManager );
        StringTypeSerializer stringTypeSerializer = new StringTypeSerializer();
        BooleanTypeSerializer booleanTypeSerializer = new BooleanTypeSerializer();
        ColorTypeSerializer colorTypeSerializer = new ColorTypeSerializer();
        DoubleTypeSerializer doubleTypeSerializer = new DoubleTypeSerializer();
        IntegerTypeSerializer integerTypeSerializer = new IntegerTypeSerializer();
        EnumTypeSerializer enumTypeSerializer = new EnumTypeSerializer( definitionUtils );
        List<Bpmn2OryxPropertySerializer<?>> propertySerializers = new LinkedList<>();
        propertySerializers.add( stringTypeSerializer );
        propertySerializers.add( booleanTypeSerializer );
        propertySerializers.add( colorTypeSerializer );
        propertySerializers.add( doubleTypeSerializer );
        propertySerializers.add( integerTypeSerializer );
        propertySerializers.add( enumTypeSerializer );
        oryxPropertyManager = new Bpmn2OryxPropertyManager( propertySerializers );
        oryxManager = new Bpmn2OryxManager( oryxIdMappings, oryxPropertyManager );
        oryxManager.init();
        
        // Marshalling factories.
        objectBuilderFactory = new BPMNGraphObjectBuilderFactory( definitionManager, oryxManager );

        RuntimeBindableMorphAdapterFactory morphAdapterFactory = new RuntimeBindableMorphAdapterFactory( applicationFactoryManager );
        taskMorphAdapter = new TaskMorphAdapter( morphAdapterFactory );
        final Collection morphAdapters = new ArrayList( 1 ) {{
            add( taskMorphAdapter );
        }};
        when(definitionManager.getMorphAdapters( any(Class.class) )).thenReturn( morphAdapters );
        
        
        // The tested BPMN marshaller.
        tested = new BPMNDiagramMarshaller( objectBuilderFactory, definitionManager, graphUtils, 
                oryxManager, applicationFactoryManager, commandManager, commandFactory);
    }

    // 4 nodes expected: BPMNDiagram, StartNode, Task and EndNode
    @Test
    @SuppressWarnings("unchecked")
    public void testUnmarshallBasic() throws Exception  {
        Diagram<Graph, Settings> diagram = unmarshall(BPMN_BASIC);
        assertDiagram( diagram, 4 );
        assertEquals( "Basic process", diagram.getSettings().getTitle() );
        Node<? extends Definition, ? > task1 = diagram.getGraph().getNode("810797AB-7D09-4E1F-8A5B-96C424E4B031");
        assertTrue( task1.getContent().getDefinition() instanceof NoneTask);
    }
    
    @Test
    @SuppressWarnings("unchecked")
    public void testUmarshallEvaluation() throws Exception  {
        Diagram<Graph, Settings> diagram = unmarshall(BPMN_EVALUATION);
        assertDiagram( diagram, 8 );
        assertEquals( "Evaluation", diagram.getSettings().getTitle() );
        Node<? extends Definition, ? > task1 = diagram.getGraph().getNode("_88233779-B395-4B8C-A086-9EF43698426C");
        Node<? extends Definition, ? > task2 = diagram.getGraph().getNode("_AE5BF0DC-B720-4FDE-9499-5ED89D41FB1A");
        Node<? extends Definition, ? > task3 = diagram.getGraph().getNode("_6063D302-9D81-4C86-920B-E808A45377C2");
        assertTrue( task1.getContent().getDefinition() instanceof UserTask );
        assertTrue( task2.getContent().getDefinition() instanceof UserTask );
        assertTrue( task3.getContent().getDefinition() instanceof UserTask );
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testUmarshallNotBoundaryEvents() throws Exception  {
        Diagram<Graph, Settings> diagram = unmarshall( BPMN_NOT_BOUNDARY_EVENTS );
        assertEquals( "Not Boundary Event", diagram.getSettings().getTitle() );
        assertDiagram( diagram, 6 );
        // Assert than the intermediate event is connected using a view connector, 
        // so not boundary to the task ( not docked ).
        Node event = diagram.getGraph().getNode("_CB178D55-8DC2-4CAA-8C42-4F5028D4A1F6");
        List<Edge> inEdges = event.getInEdges();
        boolean foundViewConnector = false;
        for ( Edge e : inEdges ) {
            if ( e.getContent() instanceof ViewConnector ) {
                foundViewConnector = true;
            }
        }
        assertTrue( foundViewConnector );

        // Assert absolute position as the node is not docked.
        Bounds bounds = ( (View) event.getContent()).getBounds();
        Bounds.Bound ul = bounds.getUpperLeft();
        Bounds.Bound lr = bounds.getLowerRight();
        assertEquals( 305, ul.getX(), 0 );
        assertEquals( 300, ul.getY(), 0 );
        assertEquals( 335, lr.getX(), 0 );
        assertEquals( 330, lr.getY(), 0 );
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testUmarshallBoundaryEvents() throws Exception  {
        Diagram<Graph, Settings> diagram = unmarshall( BPMN_BOUNDARY_EVENTS );
        
        // Basic assertions.
        assertEquals( "Boundary Event", diagram.getSettings().getTitle() );
        assertDiagram( diagram, 6 );

        // Assert than the intermediate event is connected using a dock connector, 
        // so boundary to the task.
        Node event = diagram.getGraph().getNode("_CB178D55-8DC2-4CAA-8C42-4F5028D4A1F6");
        List<Edge> inEdges = event.getInEdges();
        boolean foundDockConector = false;
        for ( Edge e : inEdges ) {
            if ( e.getContent() instanceof Dock) {
                foundDockConector = true;
            }
        }
        assertTrue( foundDockConector );

        // Assert relative position for the docked node.
        Bounds bounds = ( (View) event.getContent()).getBounds();
        Bounds.Bound ul = bounds.getUpperLeft();
        Bounds.Bound lr = bounds.getLowerRight();
        assertEquals( 57, ul.getX(), 0 );
        assertEquals( 70, ul.getY(), 0 );
        assertEquals( 87, lr.getX(), 0 );
        assertEquals( 100, lr.getY(), 0 );
    }
    
    @Test
    public void testUnmarshallSeveralDiagrams() throws Exception  {
        Diagram<Graph, Settings> diagram1 = unmarshall(BPMN_EVALUATION);
        assertDiagram( diagram1, 8 );
        assertEquals( "Evaluation", diagram1.getSettings().getTitle() );
        Diagram<Graph, Settings> diagram = unmarshall(BPMN_LANES);
        assertDiagram( diagram, 7 );
        assertEquals( "Lanes test", diagram.getSettings().getTitle() );
    }

    @Test
    public void testMarshallBasic() throws Exception {
        Diagram<Graph, Settings> diagram = unmarshall(BPMN_BASIC);
        String result = tested.marshall(diagram);
        assertDiagram( result, 1, 3, 2);
    }
    
    @Test
    public void testMarshallEvaluation() throws Exception  {
        Diagram<Graph, Settings> diagram = unmarshall(BPMN_EVALUATION);
        String result = tested.marshall(diagram);
        assertDiagram( result, 1, 7, 7);
    }

    @Test
    public void testMarshallNotBoundaryEvents() throws Exception  {
        Diagram<Graph, Settings> diagram = unmarshall(BPMN_NOT_BOUNDARY_EVENTS);
        String result = tested.marshall(diagram);
        assertDiagram( result, 1, 5, 4);
    }

    @Test
    public void testMarshallBoundaryEvents() throws Exception  {
        Diagram<Graph, Settings> diagram = unmarshall(BPMN_BOUNDARY_EVENTS);
        String result = tested.marshall(diagram);
        assertDiagram( result, 1, 5, 3);
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
    
    private Diagram<Graph, Settings> unmarshall(String fileName) throws Exception  {
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
