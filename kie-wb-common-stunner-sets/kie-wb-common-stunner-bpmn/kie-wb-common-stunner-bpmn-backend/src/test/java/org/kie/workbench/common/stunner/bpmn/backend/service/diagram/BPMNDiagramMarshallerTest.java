package org.kie.workbench.common.stunner.bpmn.backend.service.diagram;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kie.workbench.common.stunner.bpmn.backend.marshall.json.oryx.property.*;
import org.kie.workbench.common.stunner.bpmn.definition.*;
import org.kie.workbench.common.stunner.core.graph.content.view.View;
import org.kie.workbench.common.stunner.core.graph.content.view.ViewConnector;
import org.kie.workbench.common.stunner.core.graph.content.view.ViewConnectorImpl;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.kie.workbench.common.stunner.backend.ApplicationFactoryManager;
import org.kie.workbench.common.stunner.backend.definition.factory.TestScopeModelFactory;
import org.kie.workbench.common.stunner.bpmn.BPMNDefinitionSet;
import org.kie.workbench.common.stunner.bpmn.backend.marshall.json.builder.BPMNGraphObjectBuilderFactory;
import org.kie.workbench.common.stunner.bpmn.backend.marshall.json.oryx.Bpmn2OryxIdMappings;
import org.kie.workbench.common.stunner.bpmn.backend.marshall.json.oryx.Bpmn2OryxManager;
import org.kie.workbench.common.stunner.bpmn.definition.property.assignee.AssigneeSet;
import org.kie.workbench.common.stunner.bpmn.definition.property.dataio.AssignmentsInfo;
import org.kie.workbench.common.stunner.bpmn.definition.property.dataio.DataIOSet;
import org.kie.workbench.common.stunner.bpmn.definition.property.diagram.DiagramSet;
import org.kie.workbench.common.stunner.bpmn.definition.property.general.BPMNGeneral;
import org.kie.workbench.common.stunner.bpmn.definition.property.variables.ProcessVariables;
import org.kie.workbench.common.stunner.core.api.DefinitionManager;
import org.kie.workbench.common.stunner.core.backend.definition.adapter.annotation.RuntimeDefinitionAdapter;
import org.kie.workbench.common.stunner.core.backend.definition.adapter.annotation.RuntimeDefinitionSetAdapter;
import org.kie.workbench.common.stunner.core.backend.definition.adapter.annotation.RuntimePropertyAdapter;
import org.kie.workbench.common.stunner.core.backend.definition.adapter.annotation.RuntimePropertySetAdapter;
import org.kie.workbench.common.stunner.core.backend.definition.adapter.binding.RuntimeBindableMorphAdapter;
import org.kie.workbench.common.stunner.core.command.CommandManagerFactory;
import org.kie.workbench.common.stunner.core.command.impl.CommandManagerFactoryImpl;
import org.kie.workbench.common.stunner.core.definition.adapter.AdapterManager;
import org.kie.workbench.common.stunner.core.definition.adapter.binding.BindableAdapterUtils;
import org.kie.workbench.common.stunner.core.definition.morph.MorphDefinition;
import org.kie.workbench.common.stunner.core.definition.util.DefinitionUtils;
import org.kie.workbench.common.stunner.core.diagram.Diagram;
import org.kie.workbench.common.stunner.core.diagram.DiagramImpl;
import org.kie.workbench.common.stunner.core.diagram.Settings;
import org.kie.workbench.common.stunner.core.diagram.SettingsImpl;
import org.kie.workbench.common.stunner.core.factory.graph.EdgeFactory;
import org.kie.workbench.common.stunner.core.factory.graph.ElementFactory;
import org.kie.workbench.common.stunner.core.factory.graph.GraphFactory;
import org.kie.workbench.common.stunner.core.factory.graph.NodeFactory;
import org.kie.workbench.common.stunner.core.factory.impl.EdgeFactoryImpl;
import org.kie.workbench.common.stunner.core.factory.impl.GraphFactoryImpl;
import org.kie.workbench.common.stunner.core.factory.impl.NodeFactoryImpl;
import org.kie.workbench.common.stunner.core.graph.Edge;
import org.kie.workbench.common.stunner.core.graph.Element;
import org.kie.workbench.common.stunner.core.graph.Graph;
import org.kie.workbench.common.stunner.core.graph.Node;
import org.kie.workbench.common.stunner.core.graph.command.GraphCommandManager;
import org.kie.workbench.common.stunner.core.graph.command.GraphCommandManagerImpl;
import org.kie.workbench.common.stunner.core.graph.command.factory.GraphCommandFactory;
import org.kie.workbench.common.stunner.core.graph.command.factory.GraphCommandFactoryImpl;
import org.kie.workbench.common.stunner.core.graph.content.Bounds;
import org.kie.workbench.common.stunner.core.graph.content.definition.Definition;
import org.kie.workbench.common.stunner.core.graph.content.relationship.Dock;
import org.kie.workbench.common.stunner.core.graph.impl.EdgeImpl;
import org.kie.workbench.common.stunner.core.graph.impl.NodeImpl;
import org.kie.workbench.common.stunner.core.graph.util.GraphUtils;
import org.kie.workbench.common.stunner.core.registry.definition.AdapterRegistry;

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

    protected static final String BPMN_BASIC = "org/kie/workbench/common/stunner/bpmn/backend/service/diagram/basic.bpmn";
    protected static final String BPMN_EVALUATION = "org/kie/workbench/common/stunner/bpmn/backend/service/diagram/evaluation.bpmn";
    protected static final String BPMN_LANES = "org/kie/workbench/common/stunner/bpmn/backend/service/diagram/lanes.bpmn";
    protected static final String BPMN_BOUNDARY_EVENTS = "org/kie/workbench/common/stunner/bpmn/backend/service/diagram/boundaryIntmEvent.bpmn";
    protected static final String BPMN_NOT_BOUNDARY_EVENTS = "org/kie/workbench/common/stunner/bpmn/backend/service/diagram/notBoundaryIntmEvent.bpmn";
    protected static final String BPMN_PROCESSVARIABLES = "org/kie/workbench/common/stunner/bpmn/backend/service/diagram/processVariables.bpmn";
    protected static final String BPMN_USERTASKASSIGNMENTS = "org/kie/workbench/common/stunner/bpmn/backend/service/diagram/userTaskAssignments.bpmn";
    protected static final String BPMN_PROCESSPROPERTIES = "org/kie/workbench/common/stunner/bpmn/backend/service/diagram/processProperties.bpmn";
    protected static final String BPMN_BUSINESSRULETASKRULEFLOWGROUP = "org/kie/workbench/common/stunner/bpmn/backend/service/diagram/businessRuleTask.bpmn";
    protected static final String BPMN_REUSABLE_SUBPROCESS = "org/kie/workbench/common/stunner/bpmn/backend/service/diagram/reusableSubprocessCalledElement.bpmn";
    protected static final String BPMN_SCRIPTTASK = "org/kie/workbench/common/stunner/bpmn/backend/service/diagram/scriptTask.bpmn";
    protected static final String BPMN_USERTASKASSIGNEES = "org/kie/workbench/common/stunner/bpmn/backend/service/diagram/userTaskAssignees.bpmn";
    protected static final String BPMN_SEQUENCEFLOW = "org/kie/workbench/common/stunner/bpmn/backend/service/diagram/sequenceFlow.bpmn";

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

        ProcessVariables variables = null;
        Iterator<Element> it = diagram.getGraph().nodes().iterator();
        while(it.hasNext()) {
            Element element = it.next();
            if (element.getContent() instanceof View) {
                Object oDefinition = ((View) element.getContent()).getDefinition();
                if (oDefinition instanceof BPMNDiagram) {
                    BPMNDiagram bpmnDiagram = (BPMNDiagram) oDefinition;
                    variables = bpmnDiagram.getProcessData().getProcessVariables();
                    break;
                }
            }
        }
        assertEquals( variables.getValue(), "employee:java.lang.String,reason:java.lang.String,performance:java.lang.String" );

        Node<? extends Definition, ?> diagramNode = diagram.getGraph().getNode("_luRBMdEjEeWXpsZ1tNStKQ");
        assertTrue( diagramNode.getContent().getDefinition() instanceof BPMNDiagram );
        BPMNDiagram bpmnDiagram = ( BPMNDiagram ) diagramNode.getContent().getDefinition();
        assertTrue(bpmnDiagram.getProcessData() != null);
        assertTrue(bpmnDiagram.getProcessData().getProcessVariables() != null);
        variables = bpmnDiagram.getProcessData().getProcessVariables();
        assertEquals( variables.getValue(), "employee:java.lang.String,reason:java.lang.String,performance:java.lang.String" );

    }

    @Test
    @SuppressWarnings( "unchecked" )
    public void testUnmarshallProcessProperties() throws Exception {
        Diagram<Graph, Settings> diagram = unmarshall( BPMN_PROCESSPROPERTIES );
        assertDiagram( diagram, 4 );
        assertEquals( "BPSimple", diagram.getSettings().getTitle() );

        BPMNGeneral generalProperties = null;
        DiagramSet diagramProperties = null;
        Iterator<Element> it = diagram.getGraph().nodes().iterator();
        while(it.hasNext()) {
            Element element = it.next();
            if (element.getContent() instanceof View) {
                Object oDefinition = ((View) element.getContent()).getDefinition();
                if (oDefinition instanceof BPMNDiagram) {
                    BPMNDiagram bpmnDiagram = (BPMNDiagram) oDefinition;
                    generalProperties = bpmnDiagram.getGeneral();
                    diagramProperties = bpmnDiagram.getDiagramSet();
                    break;
                }
            }
        }
        assertEquals("BPSimple", generalProperties.getName().getValue());
        assertEquals("\n" +
                "        This is a simple process\n" +
                "    ", generalProperties.getDocumentation().getValue());
        assertEquals("JDLProj.BPSimple", diagramProperties.getId().getValue());
        assertEquals(  "org.jbpm", diagramProperties.getPackageProperty().getValue() );
        assertEquals( Boolean.valueOf(true), diagramProperties.getExecutable().getValue());
    }

    @Test
    @SuppressWarnings( "unchecked" )
    public void testUnmarshallUserTaskAssignments() throws Exception {
        Diagram<Graph, Settings> diagram = unmarshall( BPMN_USERTASKASSIGNMENTS );
        assertDiagram( diagram, 8 );
        assertEquals( "UserTaskAssignments", diagram.getSettings().getTitle() );

        Node<? extends Definition, ?> selfEvaluationNode = diagram.getGraph().getNode( "_6063D302-9D81-4C86-920B-E808A45377C2");
        UserTask selfEvaluationTask = (UserTask) selfEvaluationNode.getContent().getDefinition();
        DataIOSet dataIOSet = selfEvaluationTask.getDataIOSet();

        AssignmentsInfo assignmentsinfo = dataIOSet.getAssignmentsinfo();
        assertEquals(assignmentsinfo.getValue(), "|reason:com.test.Reason,Comment:Object,Skippable:Object||performance:Object|[din]reason->reason,[dout]performance->performance");
    }

    @Test
    @SuppressWarnings( "unchecked" )
    public void testUnmarshallUserTaskAssignees() throws Exception {
        Diagram<Graph, Settings> diagram = unmarshall(BPMN_USERTASKASSIGNEES);
        assertDiagram( diagram, 6 );
        assertEquals( "UserGroups", diagram.getSettings().getTitle() );

        AssigneeSet assigneeSet = null;
        Iterator<Element> it = diagram.getGraph().nodes().iterator();
        while(it.hasNext()) {
            Element element = it.next();
            if (element.getContent() instanceof View) {
                Object oDefinition = ((View) element.getContent()).getDefinition();
                if (oDefinition instanceof UserTask) {
                    UserTask userTask = (UserTask) oDefinition;
                    assigneeSet = userTask.getAssigneeSet();
                    break;
                }
            }
        }
        assertEquals( "user,user1",  assigneeSet.getActors().getValue() );
        assertEquals( "admin,kiemgmt",  assigneeSet.getGroupid().getValue());
    }

    @Test
    @SuppressWarnings( "unchecked" )
    public void testUmarshallNotBoundaryEvents() throws Exception {
        Diagram<Graph, Settings> diagram = unmarshall( BPMN_NOT_BOUNDARY_EVENTS );
        assertEquals("Not Boundary Event", diagram.getSettings().getTitle());
        assertDiagram(diagram, 6);
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
        String result = tested.marshall(diagram);
        assertDiagram(result, 1, 3, 2);
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
        assertTrue(result.contains("<bpmn2:property id=\"performance\" itemSubjectRef=\"_performanceItem\"/>"));

    }

    @Test
    public void testMarshallProcessProperties() throws Exception {
        Diagram<Graph, Settings> diagram = unmarshall( BPMN_PROCESSPROPERTIES );
        String result = tested.marshall( diagram );
        assertDiagram(result, 1, 3, 2);
        assertTrue( result.contains( "bpmn2:process id=\"JDLProj.BPSimple\" drools:packageName=\"org.jbpm\" drools:version=\"1.0\" name=\"BPSimple\" isExecutable=\"true\"" ) );

    }

    @Test
    public void testMarshallUserTaskAssignments() throws Exception {
        Diagram<Graph, Settings> diagram = unmarshall( BPMN_USERTASKASSIGNMENTS );
        String result = tested.marshall( diagram );
        assertDiagram( result, 1, 7, 7 );

        assertTrue( result.contains( "<bpmn2:dataInput id=\"_6063D302-9D81-4C86-920B-E808A45377C2_reasonInputX\" drools:dtype=\"com.test.Reason\" itemSubjectRef=\"__6063D302-9D81-4C86-920B-E808A45377C2_reasonInputXItem\" name=\"reason\"/>" ) );
        assertTrue( result.contains( "<bpmn2:dataOutput id=\"_6063D302-9D81-4C86-920B-E808A45377C2_performanceOutputX\" drools:dtype=\"Object\" itemSubjectRef=\"__6063D302-9D81-4C86-920B-E808A45377C2_performanceOutputXItem\" name=\"performance\"/>" ) );
        assertTrue( result.contains( "<bpmn2:dataOutput id=\"_6063D302-9D81-4C86-920B-E808A45377C2_performanceOutputX\" drools:dtype=\"Object\" itemSubjectRef=\"__6063D302-9D81-4C86-920B-E808A45377C2_performanceOutputXItem\" name=\"performance\"/>" ) );

        assertTrue( result.contains("<bpmn2:sourceRef>reason</bpmn2:sourceRef>"));
        assertTrue( result.contains("<bpmn2:targetRef>_6063D302-9D81-4C86-920B-E808A45377C2_reasonInputX</bpmn2:targetRef>"));
        assertTrue(result.contains("<bpmn2:sourceRef>_6063D302-9D81-4C86-920B-E808A45377C2_performanceOutputX</bpmn2:sourceRef>"));
        assertTrue( result.contains("<bpmn2:targetRef>performance</bpmn2:targetRef>"));
    }

    @Test
    public void testMarshallUserTaskAssignees() throws Exception {
        Diagram<Graph, Settings> diagram = unmarshall(BPMN_USERTASKASSIGNEES);
        String result = tested.marshall( diagram );
        assertDiagram(result, 1, 5, 4);

        assertTrue( result.contains( "<![CDATA[admin,kiemgmt]]>" ) );
        result = result.replace('\n', ' ');
        assertTrue( result.matches("(.*)<bpmn2:resourceAssignmentExpression(.*)>user</bpmn2:formalExpression>(.*)"));
        assertTrue( result.matches("(.*)<bpmn2:resourceAssignmentExpression(.*)>user1</bpmn2:formalExpression>(.*)"));
    }

    @Test
    public void testMarshallEvaluationTwice() throws Exception {
        Diagram diagram = unmarshall( BPMN_EVALUATION );
        String result = tested.marshall( diagram );
        assertDiagram( result, 1, 7, 7 );
        Diagram diagram2 = unmarshall( BPMN_EVALUATION );
        String result2 = tested.marshall( diagram2 );
        assertDiagram(result2, 1, 7, 7);
    }

    @Test
    @SuppressWarnings( "unchecked" )
    public void testMarshallBusinessRuleTask() throws Exception {
        Diagram<Graph, Settings> diagram = unmarshall(BPMN_BUSINESSRULETASKRULEFLOWGROUP);

        BusinessRuleTask businessRuleTask = null;
        Iterator<Element> it = diagram.getGraph().nodes().iterator();
        while (it.hasNext()) {
            Element element = it.next();
            if (element.getContent() instanceof View) {
                Object oDefinition = ((View) element.getContent()).getDefinition();
                if (oDefinition instanceof BusinessRuleTask) {
                    businessRuleTask = (BusinessRuleTask) oDefinition;
                    break;
                }
            }
        }

        assertNotNull(businessRuleTask);
        assertNotNull(businessRuleTask.getExecutionSet());
        assertNotNull(businessRuleTask.getExecutionSet().getRuleFlowGroup());
        assertNotNull(businessRuleTask.getGeneral());
        assertNotNull(businessRuleTask.getGeneral().getName());

        assertEquals("my business rule task", businessRuleTask.getGeneral().getName().getValue());
        assertEquals("my-ruleflow-group", businessRuleTask.getExecutionSet().getRuleFlowGroup().getValue());
    }

    @Test
    public void testMarshallReusableSubprocess() throws Exception {
        Diagram<Graph, Settings> diagram = unmarshall(BPMN_REUSABLE_SUBPROCESS);
        ReusableSubprocess reusableSubprocess = null;

        Iterator<Element> it = diagram.getGraph().nodes().iterator();
        while (it.hasNext()) {
            Element element = it.next();
            if (element.getContent() instanceof View) {
                Object oDefinition = ((View) element.getContent()).getDefinition();
                if (oDefinition instanceof ReusableSubprocess) {
                    reusableSubprocess = (ReusableSubprocess) oDefinition;
                    break;
                }
            }
        }

        assertNotNull(reusableSubprocess);
        assertNotNull(reusableSubprocess.getExecutionSet());
        assertNotNull(reusableSubprocess.getExecutionSet().getCalledElement());
        assertNotNull(reusableSubprocess.getGeneral());
        assertNotNull(reusableSubprocess.getGeneral().getName());

        assertEquals("my subprocess", reusableSubprocess.getGeneral().getName().getValue());
        assertEquals("my-called-element", reusableSubprocess.getExecutionSet().getCalledElement().getValue());
    }

    @Test
    public void testMarshallScriptTask() throws Exception {
        Diagram<Graph, Settings> diagram = unmarshall(BPMN_SCRIPTTASK);
        ScriptTask scriptTask = null;

        Iterator<Element> it = diagram.getGraph().nodes().iterator();
        while (it.hasNext()) {
            Element element = it.next();
            if (element.getContent() instanceof View) {
                Object oDefinition = ((View) element.getContent()).getDefinition();
                if (oDefinition instanceof ScriptTask) {
                    scriptTask = (ScriptTask) oDefinition;
                    break;
                }
            }
        }

        assertNotNull(scriptTask);
        assertNotNull(scriptTask.getExecutionSet());
        assertNotNull(scriptTask.getExecutionSet().getScript());
        assertNotNull(scriptTask.getExecutionSet().getScriptLanguage());
        assertNotNull(scriptTask.getGeneral());
        assertNotNull(scriptTask.getGeneral().getName());

        assertEquals("my script task", scriptTask.getGeneral().getName().getValue());
        assertEquals("System.out.println(\"hello\");", scriptTask.getExecutionSet().getScript().getValue());
        assertEquals("java", scriptTask.getExecutionSet().getScriptLanguage().getValue());

    }

    @Test
    public void testMarshallSequenceFlow() throws Exception {
        Diagram<Graph, Settings> diagram = unmarshall(BPMN_SEQUENCEFLOW);
        SequenceFlow sequenceFlow = null;

        Iterator<Element> it = diagram.getGraph().nodes().iterator();
        while (it.hasNext()) {
            Element element = it.next();
            if (element.getContent() instanceof View) {
                Object oDefinition = ((View) element.getContent()).getDefinition();
                if (oDefinition instanceof BusinessRuleTask) {
                    sequenceFlow = (SequenceFlow) ((ViewConnectorImpl ) ((EdgeImpl) ((NodeImpl) element).getOutEdges().get(0)).getContent()).getDefinition();
                    break;
                }
            }
        }

        assertNotNull(sequenceFlow);
        assertNotNull(sequenceFlow.getExecutionSet());
        assertNotNull(sequenceFlow.getExecutionSet().getConditionExpression());
        assertNotNull(sequenceFlow.getExecutionSet().getConditionExpressionLanguage());
        assertNotNull(sequenceFlow.getExecutionSet().getPriority());
        assertNotNull(sequenceFlow.getGeneral());
        assertNotNull(sequenceFlow.getGeneral().getName());

        assertEquals("my sequence flow", sequenceFlow.getGeneral().getName().getValue());
        assertEquals("System.out.println(\"hello\");", sequenceFlow.getExecutionSet().getConditionExpression().getValue());
        assertEquals("java", sequenceFlow.getExecutionSet().getConditionExpressionLanguage().getValue());
        assertEquals("1", sequenceFlow.getExecutionSet().getPriority().getValue());

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
