package org.wirez.bpmn.backend.marshall.json.builder.nodes.events;


import org.wirez.bpmn.api.EndNoneEvent;
import org.wirez.bpmn.backend.marshall.json.builder.*;
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.content.view.View;
import org.wirez.core.api.service.definition.DefinitionService;

import javax.enterprise.context.Dependent;

@Dependent
public class EndNoneEventBuilder extends AbstractNodeBuilder<EndNoneEvent, Node<View<EndNoneEvent>, Edge>> {
    
    public EndNoneEventBuilder() {
        super();
    }

    @Override
    public String getDefinitionId() {
        return EndNoneEvent.ID;
    }

    @Override
    protected void setSize(BuilderContext context, Node<View<EndNoneEvent>, Edge> node, double width, double height) {
        super.setSize(context, node, width, height);
        EndNoneEvent def = node.getContent().getDefinition();
        def.getRadius().setValue(width  / 2);
    }
    
    @Override
    public String toString() {
        return "[NodeBuilder=EndNoneEventBuilder]" + super.toString();
    }
}
