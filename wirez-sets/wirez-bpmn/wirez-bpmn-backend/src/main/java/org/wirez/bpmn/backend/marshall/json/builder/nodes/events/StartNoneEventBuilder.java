package org.wirez.bpmn.backend.marshall.json.builder.nodes.events;

import org.wirez.bpmn.api.StartNoneEvent;
import org.wirez.bpmn.backend.marshall.json.builder.*;
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.content.ConnectionContent;
import org.wirez.core.api.graph.content.ViewContent;
import org.wirez.core.api.service.definition.DefinitionService;

public class StartNoneEventBuilder extends AbstractNodeBuilder<StartNoneEvent, Node<ViewContent<StartNoneEvent>, Edge>> {
    
    public StartNoneEventBuilder(BPMNGraphObjectBuilderFactory wiresFactory) {
        super(wiresFactory);
    }

    @Override
    protected Node<ViewContent<StartNoneEvent>, Edge> buildNode(BuilderContext context, DefinitionService definitionService) {
        return (Node<ViewContent<StartNoneEvent>, Edge>) definitionService.buildGraphElement(StartNoneEvent.ID);
    }

    @Override
    public void setSourceConnectionMagnetIndex(BuilderContext context, Node<ViewContent<StartNoneEvent>, Edge> node, Edge<ConnectionContent<StartNoneEvent>, Node> edge) {
        super.setSourceConnectionMagnetIndex(context, node, edge);
        edge.getContent().setSourceMagnetIndex(5);
        
    }

    @Override
    public void setTargetConnectionMagnetIndex(BuilderContext context, Node<ViewContent<StartNoneEvent>, Edge> node, Edge<ConnectionContent<StartNoneEvent>, Node> edge) {
        super.setTargetConnectionMagnetIndex(context, node, edge);
        edge.getContent().setSourceMagnetIndex(1);
    }

    @Override
    public String toString() {
        return "[NodeBuilder=StartNoneEventBuilder]" + super.toString();
    }
}
