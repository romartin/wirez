package org.wirez.bpmn.backend.marshall.json.builder.nodes.activities;

import org.wirez.bpmn.api.ParallelGateway;
import org.wirez.bpmn.api.ParallelGateway;
import org.wirez.bpmn.api.StartNoneEvent;
import org.wirez.bpmn.backend.marshall.json.builder.AbstractNodeBuilder;
import org.wirez.bpmn.backend.marshall.json.builder.BPMNGraphObjectBuilderFactory;
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.content.ConnectionContent;
import org.wirez.core.api.graph.content.ViewContent;
import org.wirez.core.api.service.definition.DefinitionService;

public class ParallelGatewayBuilder extends AbstractNodeBuilder<ParallelGateway, Node<ViewContent<ParallelGateway>, Edge>>  {

    public ParallelGatewayBuilder(BPMNGraphObjectBuilderFactory wiresFactory) {
        super(wiresFactory);
    }

    @Override
    protected Node<ViewContent<ParallelGateway>, Edge> buildNode(BuilderContext context, DefinitionService definitionService) {
        return (Node<ViewContent<ParallelGateway>, Edge>) definitionService.buildGraphElement(ParallelGateway.ID);
    }

    @Override
    protected void setSize(BuilderContext context, Node<ViewContent<ParallelGateway>, Edge> node, double width, double height) {
        super.setSize(context, node, width, height);
        ParallelGateway def = node.getContent().getDefinition();
        def.getRadius().setValue(width  / 2);
    }

    @Override
    public void setSourceConnectionMagnetIndex(BuilderContext context, Node<ViewContent<ParallelGateway>, Edge> node, Edge<ConnectionContent<ParallelGateway>, Node> edge) {
        super.setSourceConnectionMagnetIndex(context, node, edge);
        edge.getContent().setSourceMagnetIndex(3);

    }

    @Override
    public void setTargetConnectionMagnetIndex(BuilderContext context, Node<ViewContent<ParallelGateway>, Edge> node, Edge<ConnectionContent<ParallelGateway>, Node> edge) {
        super.setTargetConnectionMagnetIndex(context, node, edge);
        edge.getContent().setTargetMagnetIndex(5);
    }

    @Override
    public String toString() {
        return "[NodeBuilder=ParallelGatewayBuilder]" + super.toString();
    }
}
