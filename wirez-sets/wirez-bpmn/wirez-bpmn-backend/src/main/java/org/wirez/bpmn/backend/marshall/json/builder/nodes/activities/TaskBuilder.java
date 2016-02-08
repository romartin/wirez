package org.wirez.bpmn.backend.marshall.json.builder.nodes.activities;

import org.wirez.bpmn.api.Task;
import org.wirez.bpmn.api.Task;
import org.wirez.bpmn.backend.marshall.json.builder.*;
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.content.ConnectionContent;
import org.wirez.core.api.graph.content.ViewContent;
import org.wirez.core.api.service.definition.DefinitionService;

public class TaskBuilder extends AbstractNodeBuilder<Task, Node<ViewContent<Task>, Edge>>  {

    public TaskBuilder(BPMNGraphObjectBuilderFactory wiresFactory) {
        super(wiresFactory);
    }

    @Override
    protected Node<ViewContent<Task>, Edge> buildNode(BuilderContext context, DefinitionService definitionService) {
        return (Node<ViewContent<Task>, Edge>) definitionService.buildGraphElement(Task.ID);
    }

    @Override
    public void setSourceConnectionMagnetIndex(BuilderContext context, Node<ViewContent<Task>, Edge> node, Edge<ConnectionContent<Task>, Node> edge) {
        super.setSourceConnectionMagnetIndex(context, node, edge);
        edge.getContent().setSourceMagnetIndex(5);

    }

    @Override
    public void setTargetConnectionMagnetIndex(BuilderContext context, Node<ViewContent<Task>, Edge> node, Edge<ConnectionContent<Task>, Node> edge) {
        super.setTargetConnectionMagnetIndex(context, node, edge);
        edge.getContent().setSourceMagnetIndex(1);
    }

    @Override
    public String toString() {
        return "[NodeBuilder=TaskBuilder]" + super.toString();
    }
}
