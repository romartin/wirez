package org.wirez.bpmn.backend.service.diagram;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.wirez.backend.ApplicationFactoryManager;
import org.wirez.backend.definition.factory.TestScopeModelFactory;
import org.wirez.bpmn.BPMNDefinitionSet;
import org.wirez.bpmn.backend.marshall.json.builder.BPMNGraphObjectBuilderFactory;
import org.wirez.bpmn.backend.marshall.json.oryx.Bpmn2OryxIdMappings;
import org.wirez.bpmn.backend.marshall.json.oryx.Bpmn2OryxManager;
import org.wirez.bpmn.backend.marshall.json.oryx.property.*;
import org.wirez.bpmn.definition.*;
import org.wirez.bpmn.definition.property.variables.GlobalVariables;
import org.wirez.bpmn.definition.property.variables.ProcessVariables;
import org.wirez.core.api.DefinitionManager;
import org.wirez.core.backend.definition.adapter.annotation.RuntimeDefinitionAdapter;
import org.wirez.core.backend.definition.adapter.annotation.RuntimeDefinitionSetAdapter;
import org.wirez.core.backend.definition.adapter.annotation.RuntimePropertyAdapter;
import org.wirez.core.backend.definition.adapter.annotation.RuntimePropertySetAdapter;
import org.wirez.core.backend.definition.adapter.binding.RuntimeBindableMorphAdapter;
import org.wirez.core.command.CommandManagerFactory;
import org.wirez.core.command.impl.CommandManagerFactoryImpl;
import org.wirez.core.definition.adapter.AdapterManager;
import org.wirez.core.definition.adapter.binding.BindableAdapterUtils;
import org.wirez.core.definition.morph.MorphDefinition;
import org.wirez.core.definition.util.DefinitionUtils;
import org.wirez.core.diagram.Diagram;
import org.wirez.core.diagram.DiagramImpl;
import org.wirez.core.diagram.Settings;
import org.wirez.core.diagram.SettingsImpl;
import org.wirez.core.factory.graph.EdgeFactory;
import org.wirez.core.factory.graph.ElementFactory;
import org.wirez.core.factory.graph.GraphFactory;
import org.wirez.core.factory.graph.NodeFactory;
import org.wirez.core.factory.impl.EdgeFactoryImpl;
import org.wirez.core.factory.impl.GraphFactoryImpl;
import org.wirez.core.factory.impl.NodeFactoryImpl;
import org.wirez.core.graph.Edge;
import org.wirez.core.graph.Graph;
import org.wirez.core.graph.Node;
import org.wirez.core.graph.command.GraphCommandManager;
import org.wirez.core.graph.command.GraphCommandManagerImpl;
import org.wirez.core.graph.command.factory.GraphCommandFactory;
import org.wirez.core.graph.command.factory.GraphCommandFactoryImpl;
import org.wirez.core.graph.content.Bounds;
import org.wirez.core.graph.content.definition.Definition;
import org.wirez.core.graph.content.relationship.Dock;
import org.wirez.core.graph.content.view.*;
import org.wirez.core.graph.util.GraphUtils;
import org.wirez.core.registry.definition.AdapterRegistry;

import javax.enterprise.inject.spi.BeanManager;
import java.io.InputStream;
import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

// TODO: Use Archillian to avoid all that CDI mockings. 
@RunWith( MockitoJUnitRunner.class )
public class BPMNDiagramMarshallerTest {

    protected static final String BPMN_BASIC = "org/wirez/bpmn/backend/service/diagram/basic.bpmn";
    protected static final String BPMN_EVALUATION = "org/wirez/bpmn/backend/service/diagram/evaluation.bpmn";
    protected static final String BPMN_LANES = "org/wirez/bpmn/backend/service/diagram/lanes.bpmn";
    protected static final String BPMN_BOUNDARY_EVENTS = "org/wirez/bpmn/backend/service/diagram/boundaryIntmEvent.bpmn";
    protected static final String BPMN_NOT_BOUNDARY_EVENTS = "org/wirez/bpmn/backend/service/diagram/notBoundaryIntmEvent.bpmn";
    protected static final String BPMN_PROCESSVARIABLES = "org/wirez/bpmn/backend/service/diagram/processVariables.bpmn";
    protected static final String BPMN_GLOBALVARIABLES = "org/wirez/bpmn/backend/service/diagram/globalVariables.bpmn";

    @Mock
    DefinitionManager definitionManager;

    @Mock
    AdapterManager adapterManager;

    @Mock
    AdapterRegistry adapterRegistry;

    @Mock
    BeanManager beanManager;

    @Mock
    ApplicationFactoryManager applicationFactoryManager;

    EdgeFactory<Object> connectionEdgeFactory;
    NodeFactory<Object> viewNodeFactory;
    DefinitionUtils definitionUtils;
    GraphUtils graphUtils;

    GraphCommandManager commandManager;
    GraphCommandFactory commandFactory;

    GraphFactory bpmnGraphFactory;

    Bpmn2OryxIdMappings oryxIdMappings;
    Bpmn2OryxPropertyManager oryxPropertyManager;
    Bpmn2OryxManager oryxManager;

    TestScopeModelFactory testScopeModelFactory;
    BPMNGraphObjectBuilderFactory objectBuilderFactory;

    TaskTypeMorphDefinition taskMorphDefinition;

    private BPMNDiagramMarshaller tested;

    @Before
    public void setup() throws Exception {

        // Graph utils.
        when( definitionManager.adapters() ).thenReturn( adapterManager );
        when( adapterManager.registry() ).thenReturn( adapterRegistry );
        definitionUtils = new DefinitionUtils( definitionManager, applicationFactoryManager );
        graphUtils = new GraphUtils( definitionManager );

        testScopeModelFactory = new TestScopeModelFactory( new BPMNDefinitionSet.BPMNDefinitionSetBuilder().build() );

        // Definition manager.        
        final RuntimeDefinitionAdapter definitionAdapter = new RuntimeDefinitionAdapter( definitionUtils );
        final RuntimeDefinitionSetAdapter definitionSetAdapter = new RuntimeDefinitionSetAdapter( definitionAdapter );
        final RuntimePropertySetAdapter propertySetAdapter = new RuntimePropertySetAdapter();
        final RuntimePropertyAdapter propertyAdapter = new RuntimePropertyAdapter();
        when( adapterManager.forDefinitionSet() ).thenReturn( definitionSetAdapter );
        when( adapterManager.forDefinition() ).thenReturn( definitionAdapter );
        when( adapterManager.forPropertySet() ).thenReturn( propertySetAdapter );
        when( adapterManager.forProperty() ).thenReturn( propertyAdapter );
        when( adapterRegistry.getDefinitionSetAdapter( any( Class.class ) ) ).thenReturn( definitionSetAdapter );
        when( adapterRegistry.getDefinitionAdapter( any( Class.class ) ) ).thenReturn( definitionAdapter );
        when( adapterRegistry.getPropertySetAdapter( any( Class.class ) ) ).thenReturn( propertySetAdapter );
        when( adapterRegistry.getPropertyAdapter( any( Class.class ) ) ).thenReturn( propertyAdapter );

        CommandManagerFactory commandManagerFactory = new CommandManagerFactoryImpl( null );

        commandManager = new GraphCommandManagerImpl( commandManagerFactory, null, null, null );
        commandFactory = new GraphCommandFactoryImpl();
        connectionEdgeFactory = new EdgeFactoryImpl( definitionManager );
        viewNodeFactory = new NodeFactoryImpl( definitionManager );

        bpmnGraphFactory = new GraphFactoryImpl( definitionManager );

        doAnswer( invocationOnMock -> {
            String id = ( String ) invocationOnMock.getArguments()[0];
            return testScopeModelFactory.build( id );
        } ).when( applicationFactoryManager ).newDefinition( anyString() );

        doAnswer( invocationOnMock -> {
            String uuid = ( String ) invocationOnMock.getArguments()[0];
            String id = ( String ) invocationOnMock.getArguments()[1];

            if ( BPMNDefinitionSet.class.getName().equals( id) ) {
                Graph graph = ( Graph ) bpmnGraphFactory.build( uuid, new BPMNDefinitionSet.BPMNDefinitionSetBuilder().build() );
                return graph;
            }

            Object model = testScopeModelFactory.accepts( id ) ? testScopeModelFactory.build( id ) : null;

            if ( null != model ) {
                Class<? extends ElementFactory> element = RuntimeDefinitionAdapter.getGraphFactory( model.getClass() );
                if ( element.isAssignableFrom( NodeFactory.class ) ) {
                    Node node = viewNodeFactory.build( uuid, model );
                    return node;
                } else if ( element.isAssignableFrom( EdgeFactory.class ) ) {
                    Edge edge = connectionEdgeFactory.build( uuid, model );
                    return edge;
                }
            }


            return null;
        } ).when( applicationFactoryManager ).newElement( anyString(), anyString() );

        doAnswer( invocationOnMock -> {
            String uuid = ( String ) invocationOnMock.getArguments()[0];
            Class type = ( Class ) invocationOnMock.getArguments()[1];
            String id = BindableAdapterUtils.getGenericClassName( type );

            if ( BPMNDefinitionSet.class.equals( type ) ) {
                Graph graph = ( Graph ) bpmnGraphFactory.build( uuid, new BPMNDefinitionSet.BPMNDefinitionSetBuilder().build() );
                return graph;
            }

            Object model = testScopeModelFactory.accepts( id ) ? testScopeModelFactory.build( id ) : null;

            if ( null != model ) {
                Class<? extends ElementFactory> element = RuntimeDefinitionAdapter.getGraphFactory( model.getClass() );
                if ( element.isAssignableFrom( NodeFactory.class ) ) {
                    Node node = viewNodeFactory.build( uuid, model );
                    return node;
                } else if ( element.isAssignableFrom( EdgeFactory.class ) ) {
                    Edge edge = connectionEdgeFactory.build( uuid, model );
                    return edge;
                }
            }

            return null;
        } ).when( applicationFactoryManager ).newElement( anyString(), any( Class.class ) );

        doAnswer( invocationOnMock -> {
            String uuid = ( String ) invocationOnMock.getArguments()[0];
            String defSetId = ( String ) invocationOnMock.getArguments()[1];
            final Graph graph = ( Graph ) applicationFactoryManager.newElement( uuid, defSetId );
            return new DiagramImpl( uuid, graph, new SettingsImpl( defSetId ) );
        } ).when( applicationFactoryManager ).newDiagram( anyString(), anyString() );

        // Bpmn 2 oryx stuff.
        oryxIdMappings = new Bpmn2OryxIdMappings( definitionManager );
        StringTypeSerializer stringTypeSerializer = new StringTypeSerializer();
        BooleanTypeSerializer booleanTypeSerializer = new BooleanTypeSerializer();
        ColorTypeSerializer colorTypeSerializer = new ColorTypeSerializer();
        DoubleTypeSerializer doubleTypeSerializer = new DoubleTypeSerializer();
        IntegerTypeSerializer integerTypeSerializer = new IntegerTypeSerializer();
        EnumTypeSerializer enumTypeSerializer = new EnumTypeSerializer( definitionUtils );
        AssignmentsTypeSerializer assignmentsTypeSerializer = new AssignmentsTypeSerializer();
        VariablesTypeSerializer variablesTypeSerializer = new VariablesTypeSerializer();
        List<Bpmn2OryxPropertySerializer<?>> propertySerializers = new LinkedList<>();
        propertySerializers.add( stringTypeSerializer );
        propertySerializers.add( booleanTypeSerializer );
        propertySerializers.add( colorTypeSerializer );
        propertySerializers.add( doubleTypeSerializer );
        propertySerializers.add( integerTypeSerializer );
        propertySerializers.add( enumTypeSerializer );
        propertySerializers.add( assignmentsTypeSerializer );
        propertySerializers.add( variablesTypeSerializer );
        oryxPropertyManager = new Bpmn2OryxPropertyManager( propertySerializers );
        oryxManager = new Bpmn2OryxManager( oryxIdMappings, oryxPropertyManager );
        oryxManager.init();

        // Marshalling factories.
        objectBuilderFactory = new BPMNGraphObjectBuilderFactory( definitionManager, oryxManager );

        taskMorphDefinition = new TaskTypeMorphDefinition();
        Collection<MorphDefinition> morphDefinitions = new ArrayList<MorphDefinition>() {{
            add( taskMorphDefinition );
        }};
        RuntimeBindableMorphAdapter<Object> morphAdapter =
                new RuntimeBindableMorphAdapter( definitionUtils, applicationFactoryManager, morphDefinitions );

        when( adapterRegistry.getMorphAdapter( eq( UserTask.class ) ) ).thenReturn( morphAdapter );
        when( adapterRegistry.getMorphAdapter( eq( NoneTask.class ) ) ).thenReturn( morphAdapter );
        when( adapterRegistry.getMorphAdapter( eq( ScriptTask.class ) ) ).thenReturn( morphAdapter );
        when( adapterRegistry.getMorphAdapter( eq( BusinessRuleTask.class ) ) ).thenReturn( morphAdapter );

        // The tested BPMN marshaller.
        tested = new BPMNDiagramMarshaller( objectBuilderFactory, definitionManager, graphUtils,
                oryxManager, applicationFactoryManager, commandManager, commandFactory );
    }

    // 4 nodes expected: BPMNDiagram, StartNode, Task and EndNode
    @Test
    @SuppressWarnings( "unchecked" )
    public void testUnmarshallBasic() throws Exception {
        Diagram<Graph, Settings> diagram = unmarshall( BPMN_BASIC );
        assertDiagram( diagram, 4 );
        assertEquals( "Basic process", diagram.getSettings().getTitle() );
        Node<? extends Definition, ?> task1 = diagram.getGraph().getNode( "810797AB-7D09-4E1F-8A5B-96C424E4B031" );
        assertTrue( task1.getContent().getDefinition() instanceof NoneTask );
    }

    @Test
    @SuppressWarnings( "unchecked" )
    public void testUmarshallEvaluation() throws Exception {
        Diagram<Graph, Settings> diagram = unmarshall( BPMN_EVALUATION );
        assertDiagram( diagram, 8 );
        assertEquals( "Evaluation", diagram.getSettings().getTitle() );
        Node<? extends View, ?> task1 = diagram.getGraph().getNode( "_88233779-B395-4B8C-A086-9EF43698426C" );
        Node<? extends View, ?> task2 = diagram.getGraph().getNode( "_AE5BF0DC-B720-4FDE-9499-5ED89D41FB1A" );
        Node<? extends View, ?> task3 = diagram.getGraph().getNode( "_6063D302-9D81-4C86-920B-E808A45377C2" );
        assertTrue( task1.getContent().getDefinition() instanceof UserTask );
        assertTrue( task2.getContent().getDefinition() instanceof UserTask );
        assertTrue( task3.getContent().getDefinition() instanceof UserTask );
        // Assert bounds.
        Bounds task1Bounds = task1.getContent().getBounds();
        Bounds.Bound task1ULBound = task1Bounds.getUpperLeft();
        Bounds.Bound task1LRBound = task1Bounds.getLowerRight();
        assertEquals( 648d, task1ULBound.getX(), 0 );
        assertEquals( 149d, task1ULBound.getY(), 0 );
        assertEquals( 784d, task1LRBound.getX(), 0 );
        assertEquals( 197d, task1LRBound.getY(), 0 );
    }

    @Test
    @SuppressWarnings( "unchecked" )
    public void testUnmarshallProcessVariables() throws Exception {
        Diagram<Graph, Settings> diagram = unmarshall( BPMN_PROCESSVARIABLES );
        assertDiagram( diagram, 8 );
        assertEquals( "ProcessVariables", diagram.getSettings().getTitle() );
        Node<? extends Definition, ?> diagramNode = diagram.getGraph().getNode( "_luRBMdEjEeWXpsZ1tNStKQ" );
        assertTrue( diagramNode.getContent().getDefinition() instanceof BPMNDiagram );
        BPMNDiagram bpmnDiagram = ( BPMNDiagram ) diagramNode.getContent().getDefinition();
        assertTrue( bpmnDiagram.getProcessData() != null );
        assertTrue( bpmnDiagram.getProcessData().getProcessVariables() != null );
        ProcessVariables variables = bpmnDiagram.getProcessData().getProcessVariables();
        assertEquals( variables.getValue(), "employee:java.lang.String,reason:java.lang.String,performance:java.lang.String" );
    }

    @Test
    @SuppressWarnings( "unchecked" )
    public void testUnmarshallGlobalVariables() throws Exception {
        Diagram<Graph, Settings> diagram = unmarshall( BPMN_GLOBALVARIABLES );
        assertDiagram( diagram, 4 );
        assertEquals( "globalVariables", diagram.getSettings().getTitle() );
        Node<? extends Definition, ?> diagramNode = diagram.getGraph().getNode( "_2assAF7uEeaNR8zxSwG7bA" );
        assertTrue( diagramNode.getContent().getDefinition() instanceof BPMNDiagram );
        BPMNDiagram bpmnDiagram = ( BPMNDiagram ) diagramNode.getContent().getDefinition();
        assertTrue( bpmnDiagram.getProcessData() != null );
        assertTrue( bpmnDiagram.getProcessData().getGlobalVariables() != null );
        GlobalVariables variables = bpmnDiagram.getProcessData().getGlobalVariables();
        assertEquals( variables.getValue(), "gVar1:Float,gVar2:java.lang.Character" );
    }

    @Test
    @SuppressWarnings( "unchecked" )
    public void testUmarshallNotBoundaryEvents() throws Exception {
        Diagram<Graph, Settings> diagram = unmarshall( BPMN_NOT_BOUNDARY_EVENTS );
        assertEquals( "Not Boundary Event", diagram.getSettings().getTitle() );
        assertDiagram( diagram, 6 );
        // Assert than the intermediate event is connected using a view connector, 
        // so not boundary to the task ( not docked ).
        Node event = diagram.getGraph().getNode( "_CB178D55-8DC2-4CAA-8C42-4F5028D4A1F6" );
        List<Edge> inEdges = event.getInEdges();
        boolean foundViewConnector = false;
        for ( Edge e : inEdges ) {
            if ( e.getContent() instanceof ViewConnector ) {
                foundViewConnector = true;
            }
        }
        assertTrue( foundViewConnector );

        // Assert absolute position as the node is not docked.
        Bounds bounds = ( ( View ) event.getContent() ).getBounds();
        Bounds.Bound ul = bounds.getUpperLeft();
        Bounds.Bound lr = bounds.getLowerRight();
        assertEquals( 305, ul.getX(), 0 );
        assertEquals( 300, ul.getY(), 0 );
        assertEquals( 335, lr.getX(), 0 );
        assertEquals( 330, lr.getY(), 0 );
    }

    @Test
    @SuppressWarnings( "unchecked" )
    public void testUmarshallBoundaryEvents() throws Exception {
        Diagram<Graph, Settings> diagram = unmarshall( BPMN_BOUNDARY_EVENTS );

        // Basic assertions.
        assertEquals( "Boundary Event", diagram.getSettings().getTitle() );
        assertDiagram( diagram, 6 );

        // Assert than the intermediate event is connected using a dock connector, 
        // so boundary to the task.
        Node event = diagram.getGraph().getNode( "_CB178D55-8DC2-4CAA-8C42-4F5028D4A1F6" );
        List<Edge> inEdges = event.getInEdges();
        boolean foundDockConector = false;
        for ( Edge e : inEdges ) {
            if ( e.getContent() instanceof Dock ) {
                foundDockConector = true;
            }
        }
        assertTrue( foundDockConector );

        // Assert relative position for the docked node.
        Bounds bounds = ( ( View ) event.getContent() ).getBounds();
        Bounds.Bound ul = bounds.getUpperLeft();
        Bounds.Bound lr = bounds.getLowerRight();
        assertEquals( 57, ul.getX(), 0 );
        assertEquals( 70, ul.getY(), 0 );
        assertEquals( 87, lr.getX(), 0 );
        assertEquals( 100, lr.getY(), 0 );
    }

    @Test
    public void testUnmarshallSeveralDiagrams() throws Exception {
        Diagram<Graph, Settings> diagram1 = unmarshall( BPMN_EVALUATION );
        assertDiagram( diagram1, 8 );
        assertEquals( "Evaluation", diagram1.getSettings().getTitle() );
        Diagram<Graph, Settings> diagram = unmarshall( BPMN_LANES );
        assertDiagram( diagram, 7 );
        assertEquals( "Lanes test", diagram.getSettings().getTitle() );
    }

    @Test
    public void testMarshallBasic() throws Exception {
        Diagram<Graph, Settings> diagram = unmarshall( BPMN_BASIC );
        String result = tested.marshall( diagram );
        assertDiagram( result, 1, 3, 2 );
    }

    @Test
    public void testMarshallEvaluation() throws Exception {
        Diagram<Graph, Settings> diagram = unmarshall( BPMN_EVALUATION );
        String result = tested.marshall( diagram );
        assertDiagram( result, 1, 7, 7 );
    }

    @Test
    public void testMarshallNotBoundaryEvents() throws Exception {
        Diagram<Graph, Settings> diagram = unmarshall( BPMN_NOT_BOUNDARY_EVENTS );
        String result = tested.marshall( diagram );
        assertDiagram( result, 1, 5, 4 );
    }

    @Test
    public void testMarshallBoundaryEvents() throws Exception {
        Diagram<Graph, Settings> diagram = unmarshall( BPMN_BOUNDARY_EVENTS );
        String result = tested.marshall( diagram );
        assertDiagram( result, 1, 5, 3 );
    }

    @Test
    public void testMarshallProcessVariables() throws Exception {
        Diagram<Graph, Settings> diagram = unmarshall( BPMN_PROCESSVARIABLES );
        String result = tested.marshall( diagram );
        assertDiagram( result, 1, 7, 7 );

        assertTrue( result.contains( "<bpmn2:itemDefinition id=\"_employeeItem\" structureRef=\"java.lang.String\"/>" ) );
        assertTrue( result.contains( "<bpmn2:itemDefinition id=\"_reasonItem\" structureRef=\"java.lang.String\"/>" ) );
        assertTrue( result.contains( "<bpmn2:itemDefinition id=\"_performanceItem\" structureRef=\"java.lang.String\"/>" ) );

        assertTrue( result.contains( "<bpmn2:property id=\"employee\" itemSubjectRef=\"_employeeItem\"/>" ) );
        assertTrue( result.contains( "<bpmn2:property id=\"reason\" itemSubjectRef=\"_reasonItem\"/>" ) );
        assertTrue( result.contains( "<bpmn2:property id=\"performance\" itemSubjectRef=\"_performanceItem\"/>" ) );

    }

    @Test
    public void testMarshallGlobalVariables() throws Exception {
        Diagram<Graph, Settings> diagram = unmarshall( BPMN_GLOBALVARIABLES );
        String result = tested.marshall( diagram );
        assertDiagram( result, 1, 3, 2 );

        assertTrue( result.contains( "<drools:global identifier=\"gVar1\" type=\"Float\"/>" ) );
        assertTrue( result.contains( "<drools:global identifier=\"gVar2\" type=\"java.lang.Character\"/>" ) );
    }

    @Test
    public void testMarshallEvaluationTwice() throws Exception {
        Diagram diagram = unmarshall( BPMN_EVALUATION );
        String result = tested.marshall( diagram );
        assertDiagram( result, 1, 7, 7 );
        Diagram diagram2 = unmarshall( BPMN_EVALUATION );
        String result2 = tested.marshall( diagram2 );
        assertDiagram( result2, 1, 7, 7 );
    }

    private void assertDiagram( String result, int diagramCount, int nodeCount, int edgeCount ) {
        int d = count( result, "<bpmndi:BPMNDiagram" );
        int n = count( result, "<bpmndi:BPMNShape" );
        int e = count( result, "<bpmndi:BPMNEdge" );
        assertEquals( diagramCount, d );
        assertEquals( nodeCount, n );
        assertEquals( edgeCount, e );
    }

    private void assertDiagram( Diagram<Graph, Settings> diagram, int nodesSize ) {
        Graph graph = diagram.getGraph();
        assertNotNull( graph );
        Iterator<Node> nodesIterable = graph.nodes().iterator();

        List<Node> nodes = new ArrayList<>();
        nodesIterable.forEachRemaining( nodes::add );
        assertEquals( nodesSize, nodes.size() );
    }

    private Diagram<Graph, Settings> unmarshall( String fileName ) throws Exception {
        InputStream is = loadStream( fileName );
        return tested.unmarhsall( is );
    }

    protected InputStream loadStream( String path ) {
        return Thread.currentThread().getContextClassLoader().getResourceAsStream( path );
    }

    protected static int count( final String string, final String substring ) {
        int count = 0;
        int idx = 0;
        while ( ( idx = string.indexOf( substring, idx ) ) != -1 ) {
            idx++;
            count++;
        }

        return count;
    }

}
