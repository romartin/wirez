package org.wirez.bpmn.backend.marshall.json.builder.nodes.events;

import org.uberfire.ext.wirez.bpmn.api.StartNoneEvent;
import org.uberfire.ext.wirez.bpmn.backend.marshall.json.builder.AbstractObjectBuilder;
import org.uberfire.ext.wirez.bpmn.backend.marshall.json.builder.BPMNGraphObjectBuilderFactory;
import org.uberfire.ext.wirez.bpmn.backend.marshall.json.builder.GraphObjectBuilder;
import org.uberfire.ext.wirez.bpmn.backend.marshall.json.builder.NodeObjectBuilder;
import org.uberfire.ext.wirez.core.api.graph.DefaultEdge;
import org.uberfire.ext.wirez.core.api.graph.DefaultNode;
import org.uberfire.ext.wirez.core.api.graph.Element;
import org.uberfire.ext.wirez.core.api.impl.graph.DefaultBound;
import org.uberfire.ext.wirez.core.api.impl.graph.DefaultBounds;

import java.util.HashMap;
import java.util.Map;

public class StartNoneEventBuilder extends AbstractObjectBuilder<StartNoneEvent, DefaultNode<StartNoneEvent, DefaultEdge>> implements NodeObjectBuilder<StartNoneEvent> {
    
    public StartNoneEventBuilder(BPMNGraphObjectBuilderFactory wiresFactory) {
        super(wiresFactory);
    }

    @Override
    public DefaultNode build(GraphObjectBuilder.BuilderContext context) {
        // TODO: Node Properties.
        final Map<String, Object> properties = new HashMap<String, Object>();

        // TODO: bounds.
        final Element.Bounds bounds =
                new DefaultBounds(
                        new DefaultBound(70d, 200d),
                        new DefaultBound(20d, 150d)
                );
        DefaultNode result = StartNoneEvent.INSTANCE.build(nodeId, properties, bounds);
        
        // Outgoing connections.
        if (outgoingNodeIds != null && !outgoingNodeIds.isEmpty()) {
            for (String outgoingNodeId : outgoingNodeIds) {
                GraphObjectBuilder<?, ?> outgoingNodeBuilder = getBuilder(context, outgoingNodeId);
                if (outgoingNodeBuilder == null) {
                    throw new RuntimeException("No edge for " + outgoingNodeId);
                }

                DefaultEdge edge = (DefaultEdge) outgoingNodeBuilder.build(context);
                result.getOutEdges().add(edge);
                edge.setSourceNode(result);
                
                // TODO: context.getGraph().addEdge(edge); ?
            }
        }
        
        return result;
    }

    @Override
    public String toString() {
        return "[NodeBuilder=StartProcessNode]" + super.toString();
    }
}
