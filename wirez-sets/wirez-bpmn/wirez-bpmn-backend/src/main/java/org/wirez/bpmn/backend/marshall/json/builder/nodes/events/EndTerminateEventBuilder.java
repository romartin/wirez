package org.wirez.bpmn.backend.marshall.json.builder.nodes.events;


import org.wirez.bpmn.api.EndTerminateEvent;
import org.wirez.bpmn.backend.marshall.json.Bpmn2OryxMappings;
import org.wirez.bpmn.backend.marshall.json.builder.AbstractNodeBuilder;
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.content.view.View;

import javax.enterprise.context.Dependent;

@Dependent
public class EndTerminateEventBuilder extends AbstractNodeBuilder<EndTerminateEvent, Node<View<EndTerminateEvent>, Edge>> {
    
    public EndTerminateEventBuilder() {
        super();
    }

    @Override
    public String getDefinitionId() {
        return Bpmn2OryxMappings.getOryxId(EndTerminateEvent.class);
    }

    @Override
    protected void setSize(BuilderContext context, Node<View<EndTerminateEvent>, Edge> node, double width, double height) {
        super.setSize(context, node, width, height);
        EndTerminateEvent def = node.getContent().getDefinition();
        def.getRadius().setValue(width  / 2);
    }
    
    @Override
    public String toString() {
        return "[NodeBuilder=EndTerminateEventBuilder]" + super.toString();
    }
}
