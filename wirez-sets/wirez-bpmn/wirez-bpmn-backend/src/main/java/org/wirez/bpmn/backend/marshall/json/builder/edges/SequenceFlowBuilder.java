package org.wirez.bpmn.backend.marshall.json.builder.edges;


import org.wirez.bpmn.api.SequenceFlow;
import org.wirez.bpmn.backend.marshall.json.builder.AbstractEdgeBuilder;
import org.wirez.bpmn.backend.marshall.json.oryx.Bpmn2OryxIdMappings;
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.content.view.View;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class SequenceFlowBuilder extends AbstractEdgeBuilder<SequenceFlow, Edge<View<SequenceFlow>, Node>> {

    @Inject
    public SequenceFlowBuilder(Bpmn2OryxIdMappings oryxIdMappings) {
        super(oryxIdMappings);
    }

    @Override
    public String getDefinitionId() {
        return oryxIdMappings.getOryxId(SequenceFlow.class);
    }
    
    @Override
    public String toString() {
        return "[EdgeBuilder=SequenceFlowBuilder]" + super.toString();
    }
    
}
