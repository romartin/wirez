package org.wirez.bpmn.backend.marshall.json.builder.nodes.activities;

import org.wirez.bpmn.api.BPMNDiagram;
import org.wirez.bpmn.api.Task;
import org.wirez.bpmn.backend.marshall.json.builder.AbstractNodeBuilder;
import org.wirez.bpmn.backend.marshall.json.oryx.Bpmn2OryxIdMappings;
import org.wirez.core.api.definition.adapter.binding.BindableAdapterUtils;
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.content.view.View;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class TaskBuilder extends AbstractNodeBuilder<Task, Node<View<Task>, Edge>>  {

    @Inject
    public TaskBuilder(Bpmn2OryxIdMappings oryxIdMappings) {
        super(oryxIdMappings);
    }

    @Override
    public String getDefinitionId() {
        return BindableAdapterUtils.getDefinitionId(Task.class);
    }

    @Override
    public String getOryxDefinitionId() {
        return oryxIdMappings.getOryxDefinitionId( Task.class );
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
