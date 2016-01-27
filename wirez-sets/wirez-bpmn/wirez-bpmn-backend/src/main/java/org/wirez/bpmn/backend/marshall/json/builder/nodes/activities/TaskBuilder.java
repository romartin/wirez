package org.wirez.bpmn.backend.marshall.json.builder.nodes.activities;

import org.uberfire.ext.wirez.bpmn.api.Task;
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

public class TaskBuilder extends AbstractObjectBuilder<Task, DefaultNode<Task, DefaultEdge>> implements NodeObjectBuilder<Task> {

    public TaskBuilder(BPMNGraphObjectBuilderFactory wiresFactory) {
        super(wiresFactory);
    }

    @Override
    public DefaultNode<Task, DefaultEdge> build(BuilderContext context) {
        // TODO: Node Properties.
        final Map<String, Object> properties = new HashMap<String, Object>();

        // TODO: bounds.
        final Element.Bounds bounds =
                new DefaultBounds(
                        new DefaultBound(150d, 200d),
                        new DefaultBound(100d, 150d)
                );

        DefaultNode result = Task.INSTANCE.build(nodeId, properties, bounds);
        
        // TODO: Node Properties.

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
        return "[NodeBuilder=TaskNodeBuilder]" + super.toString();
    }
}
