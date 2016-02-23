package org.wirez.bpmn.backend.marshall.json.builder.nodes.events;

import org.wirez.bpmn.api.StartNoneEvent;
import org.wirez.bpmn.backend.marshall.json.builder.*;
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.content.view.View;
import org.wirez.core.api.service.definition.DefinitionService;

import javax.enterprise.context.Dependent;

@Dependent
public class StartNoneEventBuilder extends AbstractNodeBuilder<StartNoneEvent, Node<View<StartNoneEvent>, Edge>> {
    
    
    public StartNoneEventBuilder() {
        super();
    }

    @Override
    public String getDefinitionId() {
        return StartNoneEvent.ID;
    }

    @Override
    protected void setSize(BuilderContext context, Node<View<StartNoneEvent>, Edge> node, double width, double height) {
        super.setSize(context, node, width, height);
        StartNoneEvent def = node.getContent().getDefinition();
        def.getRadius().setValue(width  / 2);
    }

    @Override
    public String toString() {
        return "[NodeBuilder=StartNoneEventBuilder]" + super.toString();
    }
}
