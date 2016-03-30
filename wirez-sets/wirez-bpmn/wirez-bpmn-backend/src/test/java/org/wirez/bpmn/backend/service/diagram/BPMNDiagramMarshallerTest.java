package org.wirez.bpmn.backend.service.diagram;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.uberfire.mocks.EventSourceMock;
import org.wirez.bpmn.api.*;
import org.wirez.bpmn.api.factory.BPMNDefinitionBuilder;
import org.wirez.bpmn.api.factory.BPMNDefinitionSetBuilder;
import org.wirez.bpmn.api.factory.BPMNPropertyBuilder;
import org.wirez.bpmn.api.factory.BPMNPropertySetBuilder;
import org.wirez.bpmn.backend.marshall.json.builder.BPMNGraphObjectBuilderFactory;
import org.wirez.bpmn.backend.marshall.json.builder.BootstrapObjectBuilder;
import org.wirez.bpmn.backend.marshall.json.builder.edges.SequenceFlowBuilder;
import org.wirez.bpmn.backend.marshall.json.builder.nodes.BPMNDiagramBuilder;
import org.wirez.bpmn.backend.marshall.json.builder.nodes.activities.ParallelGatewayBuilder;
import org.wirez.bpmn.backend.marshall.json.builder.nodes.activities.TaskBuilder;
import org.wirez.bpmn.backend.marshall.json.builder.nodes.events.EndNoneEventBuilder;
import org.wirez.bpmn.backend.marshall.json.builder.nodes.events.EndTerminateEventBuilder;
import org.wirez.bpmn.backend.marshall.json.builder.nodes.events.StartNoneEventBuilder;
import org.wirez.bpmn.backend.marshall.json.builder.nodes.swimlanes.LaneBuilder;
import org.wirez.core.api.DefinitionManager;
import org.wirez.core.api.adapter.PropertyAdapter;
import org.wirez.core.api.adapter.PropertySetAdapter;
import org.wirez.core.api.diagram.Diagram;
import org.wirez.core.api.diagram.Settings;
import org.wirez.core.api.event.NotificationEvent;
import org.wirez.core.api.factory.ModelBuilder;
import org.wirez.core.api.graph.Graph;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.command.GraphCommandManager;
import org.wirez.core.api.graph.command.factory.GraphCommandFactoryImpl;
import org.wirez.core.api.graph.factory.*;
import org.wirez.core.api.rule.EmptyRuleManager;
import org.wirez.core.api.service.definition.DefinitionService;
import org.wirez.core.backend.adapter.AnnotatedDefinitionAdapter;
import org.wirez.core.backend.adapter.AnnotatedPropertyAdapter;
import org.wirez.core.backend.adapter.AnnotatedPropertySetAdapter;
import org.wirez.core.backend.service.definition.DefinitionServiceImpl;

import javax.enterprise.inject.spi.BeanManager;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

// TODO: Mock the different objects created here.
@RunWith(MockitoJUnitRunner.class)
public class BPMNDiagramMarshallerTest {

    protected static final String BPMN_EVALUATION = "org/wirez/bpmn/backend/service/diagram/evaluation.bpmn";
    protected static final String BPMN_BASIC = "org/wirez/bpmn/backend/service/diagram/basic.bpmn";

    private BPMNDiagramMarshaller tested;
    
    @Mock BPMNGraphObjectBuilderFactory bpmnGraphBuilderFactory;
    @Mock DefinitionManager definitionManager;
    @Mock EventSourceMock<NotificationEvent> notificationEvent;
    @Mock BeanManager beanManager;
    private DefinitionService definitionService;

    @Before
    @SuppressWarnings("unchecked")
    public void setup() throws Exception {

        AnnotatedPropertySetAdapter propertySetAdapter = new AnnotatedPropertySetAdapter();
        AnnotatedPropertyAdapter propertyAdapter = new AnnotatedPropertyAdapter();
        List<PropertySetAdapter> propertySetAdapters = new LinkedList<>();
        propertySetAdapters.add(propertySetAdapter);
        List<PropertyAdapter> propertyAdapters = new LinkedList<>();
        propertyAdapters.add(propertyAdapter);
        
        // Bean manager & factories.
        // TODO: Mock bean manager to return the element factories.

        this.definitionService = new DefinitionServiceImpl(definitionManager);
        AnnotatedDefinitionAdapter definitionAdapter = new AnnotatedDefinitionAdapter(propertySetAdapters, 
                propertyAdapters, null);

        doAnswer(invocationOnMock -> definitionAdapter).when(definitionManager).getDefinitionAdapter(any(Object.class));
        doAnswer(invocationOnMock -> propertyAdapter).when(definitionManager).getPropertyAdapter(any(Object.class));
        doAnswer(invocationOnMock -> propertySetAdapter).when(definitionManager).getPropertySetAdapter(any(Object.class));

        List<ModelBuilder> modelFactories = new LinkedList<>();
        BPMNPropertyBuilder propertyFactory = new BPMNPropertyBuilder();
        BPMNPropertySetBuilder propertySetFactory = new BPMNPropertySetBuilder(propertyFactory);
        BPMNDefinitionBuilder definitionFactory = new BPMNDefinitionBuilder(propertyFactory, propertySetFactory);
        BPMNDefinitionSetBuilder definitionSetFactory = new BPMNDefinitionSetBuilder(definitionFactory);
        
        modelFactories.add(definitionSetFactory);
        modelFactories.add(definitionFactory);
        modelFactories.add(propertySetFactory);
        modelFactories.add(propertyFactory);
        doAnswer(invocationOnMock -> {
            String id = (String) invocationOnMock.getArguments()[0];
            
            for (ModelBuilder factory : modelFactories) {
                if ( factory.accepts(id)) {
                    return factory;
                }
            }
            
            return null;
        }).when(definitionManager).getModelFactory(anyString());
        
        BootstrapObjectBuilder bootstrapObjectBuilder = new BootstrapObjectBuilder(bpmnGraphBuilderFactory);
        doReturn(bootstrapObjectBuilder).when(bpmnGraphBuilderFactory).bootstrapBuilder();
        doAnswer(invocationOnMock -> {
            String id = (String) invocationOnMock.getArguments()[0];
            
            /* Nodes */
            if (BPMNDiagram.ID.equals(id)) {
                return new BPMNDiagramBuilder();
            } else if (StartNoneEvent.ID.equals(id)) {
                return new StartNoneEventBuilder();
            } else if (EndNoneEvent.ID.equals(id)) {
                return new EndNoneEventBuilder();
            } else if (EndTerminateEvent.ID.equals(id)) {
                return new EndTerminateEventBuilder();
            } else if (Task.ID.equals(id)) {
                return new TaskBuilder();
            } else if (ParallelGateway.ID.equals(id)) {
                return new ParallelGatewayBuilder();
            } else if (Lane.ID.equals(id)) {
                return new LaneBuilder();
            }

            /* Edges */
            if (SequenceFlow.ID.equals(id)) {
                return new SequenceFlowBuilder();
            }
            
            return null;
        }).when(bpmnGraphBuilderFactory).builderFor(anyString());
        
        this.tested = new BPMNDiagramMarshaller(bpmnGraphBuilderFactory, 
                                                definitionManager, 
                                                definitionService, 
                                                new GraphCommandManager(notificationEvent), 
                                                new EmptyRuleManager(), 
                                                new GraphCommandFactoryImpl(definitionManager));
        
    }
    
    // TODO: Add more assertions
    // @Test
    public void testUmarshallEvaluation() {
        Diagram<Settings> diagram = unmarshall(BPMN_EVALUATION);
        
        Graph graph = diagram.getGraph();
        assertNotNull(graph);
        Iterator<Node> nodesIterable = graph.nodes().iterator();
        
        List<Node> nodes = new ArrayList<>();
        nodesIterable.forEachRemaining(nodes::add);
        assertEquals(8, nodes.size());
    }
    
    private Diagram<Settings> unmarshall(String fileName) {
        InputStream is = loadStream(fileName);
        return tested.unmarhsall(is);
    }

    @Test
    public void testMarshallEvaluation() {
        //Diagram<Settings> diagram = new DiagramImpl("uuid", null, new SettingsImpl("title", "bpmnDefSet", "bpmnShapeSet"));
        Diagram<Settings> diagram = unmarshall(BPMN_EVALUATION);
        String result = tested.marshall(diagram);
        System.out.println(result);
        
    }
    
    protected InputStream loadStream(String path) {
        return Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
    }


    @Test
    public void testMarshallBasic() {
        Diagram<Settings> diagram = unmarshall(BPMN_BASIC);
        String result = tested.marshall(diagram);
        System.out.println(result);
    }

}
