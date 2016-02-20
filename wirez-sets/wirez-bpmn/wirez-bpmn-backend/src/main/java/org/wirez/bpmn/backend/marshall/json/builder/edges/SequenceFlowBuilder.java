package org.wirez.bpmn.backend.marshall.json.builder.edges;


import org.wirez.bpmn.api.SequenceFlow;
import org.wirez.bpmn.backend.marshall.json.builder.*;
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.content.view.View;
import org.wirez.core.api.service.definition.DefinitionService;

public class SequenceFlowBuilder extends AbstractEdgeBuilder<SequenceFlow, Edge<View<SequenceFlow>, Node>> {

    public SequenceFlowBuilder(BPMNGraphObjectBuilderFactory wiresFactory) {
        super(wiresFactory);
    }

    @Override
    protected Edge<View<SequenceFlow>, Node> buildEdge(BuilderContext context, DefinitionService definitionService) {
        return (Edge<View<SequenceFlow>, Node>) definitionService.buildGraphElement(this.nodeId, SequenceFlow.ID);
    }

    @Override
    public String toString() {
        return "[EdgeBuilder=SequenceFlowBuilder]" + super.toString();
    }
}
