package org.wirez.bpmn.backend.marshall.json.builder.nodes.events;

import org.uberfire.ext.wirez.bpmn.api.EndNoneEvent;
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

public class EndNoneEventBuilder extends AbstractObjectBuilder<EndNoneEvent, DefaultNode<EndNoneEvent, DefaultEdge>> implements NodeObjectBuilder<EndNoneEvent> {
    
    public EndNoneEventBuilder(BPMNGraphObjectBuilderFactory wiresFactory) {
        super(wiresFactory);
    }
    
    @Override
    public DefaultNode<EndNoneEvent, DefaultEdge> build(final GraphObjectBuilder.BuilderContext context) {
        // TODO: Node Properties.
        final Map<String, Object> properties = new HashMap<String, Object>();

        // TODO: bounds.
        final Element.Bounds bounds =
                new DefaultBounds(
                        new DefaultBound(250d, 200d),
                        new DefaultBound(200d, 150d)
                );
        
        // No outgoing connections expected.
        
        return EndNoneEvent.INSTANCE.build(nodeId, properties, bounds);
    }

    @Override
    public String toString() {
        return "[NodeBuilder=EndNoneEventBuilder]" + super.toString();
    }
}
