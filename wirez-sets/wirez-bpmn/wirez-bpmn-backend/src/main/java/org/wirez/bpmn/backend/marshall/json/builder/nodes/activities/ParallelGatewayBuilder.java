package org.wirez.bpmn.backend.marshall.json.builder.nodes.activities;

import org.wirez.bpmn.api.ParallelGateway;
import org.wirez.bpmn.backend.marshall.json.builder.AbstractNodeBuilder;
import org.wirez.bpmn.backend.marshall.json.oryx.Bpmn2OryxIdMappings;
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.content.view.View;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class ParallelGatewayBuilder extends AbstractNodeBuilder<ParallelGateway, Node<View<ParallelGateway>, Edge>>  {

    @Inject
    public ParallelGatewayBuilder(Bpmn2OryxIdMappings oryxIdMappings) {
        super(oryxIdMappings);
    }

    @Override
    public String getDefinitionId() {
        return oryxIdMappings.getOryxId(ParallelGateway.class);
    }
    
    @Override
    protected void setSize(BuilderContext context, Node<View<ParallelGateway>, Edge> node, double width, double height) {
        super.setSize(context, node, width, height);
        ParallelGateway def = node.getContent().getDefinition();
        def.getRadius().setValue(width  / 2);
    }

    @Override
    public String toString() {
        return "[NodeBuilder=ParallelGatewayBuilder]" + super.toString();
    }
    
}
