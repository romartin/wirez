package org.wirez.bpmn.backend.marshall.json.builder.edges;


import org.wirez.bpmn.api.SequenceFlow;
import org.wirez.bpmn.backend.marshall.json.Bpmn2OryxMappings;
import org.wirez.bpmn.backend.marshall.json.builder.AbstractEdgeBuilder;
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.content.view.View;

import javax.enterprise.context.Dependent;

@Dependent
public class SequenceFlowBuilder extends AbstractEdgeBuilder<SequenceFlow, Edge<View<SequenceFlow>, Node>> {

    public SequenceFlowBuilder() {
        super();
    }

    @Override
    public String getDefinitionId() {
        return Bpmn2OryxMappings.getOryxId(SequenceFlow.class);
    }
    
    @Override
    public String toString() {
        return "[EdgeBuilder=SequenceFlowBuilder]" + super.toString();
    }
    
}
