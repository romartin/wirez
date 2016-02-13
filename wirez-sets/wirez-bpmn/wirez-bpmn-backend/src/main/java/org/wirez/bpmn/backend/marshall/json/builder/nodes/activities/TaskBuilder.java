package org.wirez.bpmn.backend.marshall.json.builder.nodes.activities;

import org.wirez.bpmn.api.StartNoneEvent;
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
        return (Node<ViewContent<Task>, Edge>) definitionService.buildGraphElement(this.nodeId, Task.ID);
    }

    @Override
    protected void setSize(BuilderContext context, Node<ViewContent<Task>, Edge> node, double width, double height) {
        super.setSize(context, node, width, height);
        Task def = node.getContent().getDefinition();
        def.getWidth().setValue(width);
        def.getHeight().setValue(height);
    }

    @Override
    public void setSourceConnectionMagnetIndex(BuilderContext context, Node<ViewContent<Task>, Edge> node, Edge<ConnectionContent<Task>, Node> edge) {
        super.setSourceConnectionMagnetIndex(context, node, edge);
        edge.getContent().setSourceMagnetIndex(3);

    }

    @Override
    public void setTargetConnectionMagnetIndex(BuilderContext context, Node<ViewContent<Task>, Edge> node, Edge<ConnectionContent<Task>, Node> edge) {
        super.setTargetConnectionMagnetIndex(context, node, edge);
        edge.getContent().setTargetMagnetIndex(5);
    }

    @Override
    public String toString() {
        return "[NodeBuilder=TaskBuilder]" + super.toString();
    }
}
