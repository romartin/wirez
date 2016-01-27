package org.wirez.bpmn.backend.marshall.json.builder.edges;

import org.uberfire.ext.wirez.bpmn.api.SequenceFlow;
import org.uberfire.ext.wirez.bpmn.backend.marshall.json.builder.AbstractObjectBuilder;
import org.uberfire.ext.wirez.bpmn.backend.marshall.json.builder.BPMNGraphObjectBuilderFactory;
import org.uberfire.ext.wirez.bpmn.backend.marshall.json.builder.EdgeObjectBuilder;
import org.uberfire.ext.wirez.bpmn.backend.marshall.json.builder.GraphObjectBuilder;
import org.uberfire.ext.wirez.core.api.graph.DefaultEdge;
import org.uberfire.ext.wirez.core.api.graph.DefaultNode;
import org.uberfire.ext.wirez.core.api.graph.Element;
import org.uberfire.ext.wirez.core.api.impl.graph.DefaultBound;
import org.uberfire.ext.wirez.core.api.impl.graph.DefaultBounds;

import java.util.HashMap;
import java.util.Map;

public class SequenceFlowBuilder extends AbstractObjectBuilder<SequenceFlow, DefaultEdge<SequenceFlow, DefaultNode>> implements EdgeObjectBuilder<SequenceFlow> {

    public SequenceFlowBuilder(BPMNGraphObjectBuilderFactory wiresFactory) {
        super(wiresFactory);
    }

    @Override
    public DefaultEdge<SequenceFlow, DefaultNode> build(GraphObjectBuilder.BuilderContext context) {
        // TODO: Node Properties.
        final Map<String, Object> properties = new HashMap<String, Object>();

        // TODO: bounds.
        final Element.Bounds bounds =
                new DefaultBounds(
                        new DefaultBound(100d, 100d),
                        new DefaultBound(100d, 100d)
                );

        DefaultEdge result = SequenceFlow.INSTANCE.build(nodeId, properties, bounds);
        
        // Outgoing connections.
        if (outgoingNodeIds != null && !outgoingNodeIds.isEmpty()) {
            for (String outgoingNodeId : outgoingNodeIds) {
                GraphObjectBuilder<?, ?> outgoingNodeBuilder = getBuilder(context, outgoingNodeId);
                if (outgoingNodeBuilder == null) {
                    throw new RuntimeException("No edge for " + outgoingNodeId);
                }

                DefaultNode node = (DefaultNode) outgoingNodeBuilder.build(context);
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
