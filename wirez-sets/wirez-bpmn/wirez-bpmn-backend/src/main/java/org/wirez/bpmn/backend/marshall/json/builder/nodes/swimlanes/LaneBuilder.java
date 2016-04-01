package org.wirez.bpmn.backend.marshall.json.builder.nodes.swimlanes;

import org.wirez.bpmn.api.Lane;
import org.wirez.bpmn.backend.marshall.json.Bpmn2OryxMappings;
import org.wirez.bpmn.backend.marshall.json.builder.AbstractNodeBuilder;
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.content.view.View;

import javax.enterprise.context.Dependent;

@Dependent
public class LaneBuilder extends AbstractNodeBuilder<Lane, Node<View<Lane>, Edge>>  {

    public LaneBuilder() {
        super();
    }

    @Override
    public String getDefinitionId() {
        return Bpmn2OryxMappings.getOryxId(Lane.class);
    }

    @Override
    protected void setSize(BuilderContext context, Node<View<Lane>, Edge> node, double width, double height) {
        super.setSize(context, node, width, height);
        Lane def = node.getContent().getDefinition();
        def.getWidth().setValue(width);
        def.getHeight().setValue(height);
    }

    @Override
    public String toString() {
        return "[NodeBuilder=LaneBuilder]" + super.toString();
    }
}
