package org.wirez.bpmn.backend.marshall.json.builder.nodes.events;


import org.wirez.bpmn.api.BPMNDiagram;
import org.wirez.bpmn.api.EndTerminateEvent;
import org.wirez.bpmn.backend.marshall.json.builder.AbstractNodeBuilder;
import org.wirez.bpmn.backend.marshall.json.oryx.Bpmn2OryxIdMappings;
import org.wirez.core.api.definition.adapter.binding.BindableAdapterUtils;
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.content.view.View;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class EndTerminateEventBuilder extends AbstractNodeBuilder<EndTerminateEvent, Node<View<EndTerminateEvent>, Edge>> {
    
    @Inject
    public EndTerminateEventBuilder(Bpmn2OryxIdMappings oryxIdMappings) {
        super(oryxIdMappings);
    }

    @Override
    public String getDefinitionId() {
        return BindableAdapterUtils.getDefinitionId(EndTerminateEvent.class);
    }

    @Override
    public String getOryxDefinitionId() {
        return oryxIdMappings.getOryxDefinitionId( EndTerminateEvent.class );
    }
    
    @Override
    protected void setSize(BuilderContext context, Node<View<EndTerminateEvent>, Edge> node, double width, double height) {
        super.setSize(context, node, width, height);
        EndTerminateEvent def = node.getContent().getDefinition();
        def.getRadius().setValue(width  / 2);
    }
    
    @Override
    public String toString() {
        return "[NodeBuilder=EndTerminateEventBuilder]" + super.toString();
    }
}
