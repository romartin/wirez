package org.wirez.bpmn.backend.marshall.json.builder.nodes.events;


import org.wirez.bpmn.api.EndNoneEvent;
import org.wirez.bpmn.backend.marshall.json.builder.AbstractObjectBuilder;
import org.wirez.bpmn.backend.marshall.json.builder.BPMNGraphObjectBuilderFactory;
import org.wirez.bpmn.backend.marshall.json.builder.GraphObjectBuilder;
import org.wirez.bpmn.backend.marshall.json.builder.NodeObjectBuilder;
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.content.ViewContent;
import org.wirez.core.api.service.definition.DefinitionService;

public class EndNoneEventBuilder extends AbstractObjectBuilder<EndNoneEvent, Node<ViewContent<EndNoneEvent>, Edge>> implements NodeObjectBuilder<EndNoneEvent> {
    
    public EndNoneEventBuilder(BPMNGraphObjectBuilderFactory wiresFactory) {
        super(wiresFactory);
    }
    
    @Override
    public Node<ViewContent<EndNoneEvent>, Edge> build(final GraphObjectBuilder.BuilderContext context) {
        DefinitionService definitionService = bpmnGraphFactory.getDefinitionService();

        Node result = (Node) definitionService.buildGraphElement(EndNoneEvent.ID);
        
        // No outgoing connections expected.
        
        return result;
    }

    @Override
    public String toString() {
        return "[NodeBuilder=EndNoneEventBuilder]" + super.toString();
    }
}
