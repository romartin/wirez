package org.wirez.bpmn.backend.marshall.json.builder.nodes.events;


import org.wirez.bpmn.api.EndNoneEvent;
import org.wirez.bpmn.api.StartNoneEvent;
import org.wirez.bpmn.backend.marshall.json.builder.*;
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.content.ConnectionContent;
import org.wirez.core.api.graph.content.ViewContent;
import org.wirez.core.api.service.definition.DefinitionService;

public class EndNoneEventBuilder extends AbstractNodeBuilder<EndNoneEvent, Node<ViewContent<EndNoneEvent>, Edge>> {
    
    public EndNoneEventBuilder(BPMNGraphObjectBuilderFactory wiresFactory) {
        super(wiresFactory);
    }

    @Override
    protected Node<ViewContent<EndNoneEvent>, Edge> buildNode(BuilderContext context, DefinitionService definitionService) {
        return (Node<ViewContent<EndNoneEvent>, Edge>) definitionService.buildGraphElement(EndNoneEvent.ID);
    }

    @Override
    protected void afterNodeBuild(BuilderContext context, Node<ViewContent<EndNoneEvent>, Edge> node) {
        // Do nothing. No outgoing connections expected.
    }

    @Override
    public void setSourceConnectionMagnetIndex(BuilderContext context, Node<ViewContent<EndNoneEvent>, Edge> node, Edge<ConnectionContent<EndNoneEvent>, Node> edge) {
        super.setSourceConnectionMagnetIndex(context, node, edge);
        edge.getContent().setSourceMagnetIndex(3);

    }

    @Override
    public void setTargetConnectionMagnetIndex(BuilderContext context, Node<ViewContent<EndNoneEvent>, Edge> node, Edge<ConnectionContent<EndNoneEvent>, Node> edge) {
        super.setTargetConnectionMagnetIndex(context, node, edge);
        edge.getContent().setTargetMagnetIndex(7);
    }


    @Override
    public String toString() {
        return "[NodeBuilder=EndNoneEventBuilder]" + super.toString();
    }
}
