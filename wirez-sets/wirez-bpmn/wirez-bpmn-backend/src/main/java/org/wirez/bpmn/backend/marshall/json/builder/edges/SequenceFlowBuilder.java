package org.wirez.bpmn.backend.marshall.json.builder.edges;


import org.wirez.bpmn.api.SequenceFlow;
import org.wirez.bpmn.backend.marshall.json.builder.AbstractObjectBuilder;
import org.wirez.bpmn.backend.marshall.json.builder.BPMNGraphObjectBuilderFactory;
import org.wirez.bpmn.backend.marshall.json.builder.EdgeObjectBuilder;
import org.wirez.bpmn.backend.marshall.json.builder.GraphObjectBuilder;
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.content.ViewContent;
import org.wirez.core.api.service.definition.DefinitionService;

public class SequenceFlowBuilder extends AbstractObjectBuilder<SequenceFlow, Edge<ViewContent<SequenceFlow>, Node>> implements EdgeObjectBuilder<SequenceFlow> {

    public SequenceFlowBuilder(BPMNGraphObjectBuilderFactory wiresFactory) {
        super(wiresFactory);
    }

    @Override
    public Edge<ViewContent<SequenceFlow>, Node> build(GraphObjectBuilder.BuilderContext context) {

        DefinitionService definitionService = bpmnGraphFactory.getDefinitionService();

        Edge result = (Edge) definitionService.buildGraphElement(SequenceFlow.ID);

        // Outgoing connections.
        if (outgoingNodeIds != null && !outgoingNodeIds.isEmpty()) {
            for (String outgoingNodeId : outgoingNodeIds) {
                GraphObjectBuilder<?, ?> outgoingNodeBuilder = getBuilder(context, outgoingNodeId);
                if (outgoingNodeBuilder == null) {
                    throw new RuntimeException("No edge for " + outgoingNodeId);
                }

                Node node = (Node) outgoingNodeBuilder.build(context);
                result.setTargetNode(node);
                node.getInEdges().add(result);
                context.getGraph().addNode(node);
            }
        }

        return result;
    }

    @Override
    public String toString() {
        return "[NodeBuilder=BpmnEdgeImplNodeBuilder]" + super.toString();
    }
}
