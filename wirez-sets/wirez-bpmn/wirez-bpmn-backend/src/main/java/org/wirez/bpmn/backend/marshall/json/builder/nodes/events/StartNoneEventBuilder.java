package org.wirez.bpmn.backend.marshall.json.builder.nodes.events;

import org.wirez.bpmn.api.StartNoneEvent;
import org.wirez.bpmn.backend.marshall.json.builder.*;
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.content.view.View;
import org.wirez.core.api.service.definition.DefinitionService;

public class StartNoneEventBuilder extends AbstractNodeBuilder<StartNoneEvent, Node<View<StartNoneEvent>, Edge>> {
    
    public StartNoneEventBuilder(BPMNGraphObjectBuilderFactory wiresFactory) {
        super(wiresFactory);
    }

    @Override
    protected Node<View<StartNoneEvent>, Edge> buildNode(BuilderContext context, DefinitionService definitionService) {
        return (Node<View<StartNoneEvent>, Edge>) definitionService.buildGraphElement(this.nodeId, StartNoneEvent.ID);
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
