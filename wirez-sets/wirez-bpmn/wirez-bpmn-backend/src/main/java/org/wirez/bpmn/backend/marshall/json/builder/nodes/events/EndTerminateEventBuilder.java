package org.wirez.bpmn.backend.marshall.json.builder.nodes.events;


import org.wirez.bpmn.api.EndTerminateEvent;
import org.wirez.bpmn.backend.marshall.json.builder.AbstractNodeBuilder;
import org.wirez.bpmn.backend.marshall.json.builder.BPMNGraphObjectBuilderFactory;
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.content.view.View;
import org.wirez.core.api.service.definition.DefinitionService;

public class EndTerminateEventBuilder extends AbstractNodeBuilder<EndTerminateEvent, Node<View<EndTerminateEvent>, Edge>> {
    
    public EndTerminateEventBuilder(BPMNGraphObjectBuilderFactory wiresFactory) {
        super(wiresFactory);
    }

    @Override
    protected Node<View<EndTerminateEvent>, Edge> buildNode(BuilderContext context, DefinitionService definitionService) {
        return (Node<View<EndTerminateEvent>, Edge>) definitionService.buildGraphElement(this.nodeId, EndTerminateEvent.ID);
    }

    @Override
    protected void setSize(BuilderContext context, Node<View<EndTerminateEvent>, Edge> node, double width, double height) {
        super.setSize(context, node, width, height);
        EndTerminateEvent def = node.getContent().getDefinition();
        def.getRadius().setValue(width  / 2);
    }
    
    @Override
    protected void afterNodeBuild(BuilderContext context, Node<View<EndTerminateEvent>, Edge> node) {
        // Do nothing. No outgoing connections expected.
    }

    @Override
    public String toString() {
        return "[NodeBuilder=EndTerminateEventBuilder]" + super.toString();
    }
}
