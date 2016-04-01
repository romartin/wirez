package org.wirez.bpmn.backend.marshall.json.builder.nodes.activities;

import org.wirez.bpmn.api.Task;
import org.wirez.bpmn.backend.marshall.json.Bpmn2OryxMappings;
import org.wirez.bpmn.backend.marshall.json.builder.AbstractNodeBuilder;
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.content.view.View;

import javax.enterprise.context.Dependent;

@Dependent
public class TaskBuilder extends AbstractNodeBuilder<Task, Node<View<Task>, Edge>>  {

    public TaskBuilder() {
        super();
    }

    @Override
    public String getDefinitionId() {
        return Bpmn2OryxMappings.getOryxId(Task.class);
    }

    @Override
    protected void setSize(BuilderContext context, Node<View<Task>, Edge> node, double width, double height) {
        super.setSize(context, node, width, height);
        Task def = node.getContent().getDefinition();
        def.getWidth().setValue(width);
        def.getHeight().setValue(height);
    }

    @Override
    public String toString() {
        return "[NodeBuilder=TaskBuilder]" + super.toString();
    }
}
