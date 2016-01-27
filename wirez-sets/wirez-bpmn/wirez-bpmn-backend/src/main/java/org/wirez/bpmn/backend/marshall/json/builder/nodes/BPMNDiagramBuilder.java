package org.wirez.bpmn.backend.marshall.json.builder.nodes;

import org.uberfire.ext.wirez.bpmn.api.BPMNDiagram;
import org.uberfire.ext.wirez.bpmn.backend.marshall.json.builder.AbstractObjectBuilder;
import org.uberfire.ext.wirez.bpmn.backend.marshall.json.builder.BPMNGraphObjectBuilderFactory;
import org.uberfire.ext.wirez.bpmn.backend.marshall.json.builder.GraphObjectBuilder;
import org.uberfire.ext.wirez.bpmn.backend.marshall.json.builder.nodes.events.StartNoneEventBuilder;
import org.uberfire.ext.wirez.core.api.graph.DefaultEdge;
import org.uberfire.ext.wirez.core.api.graph.DefaultGraph;
import org.uberfire.ext.wirez.core.api.graph.DefaultNode;
import org.uberfire.ext.wirez.core.api.graph.Element;
import org.uberfire.ext.wirez.core.api.impl.graph.DefaultBound;
import org.uberfire.ext.wirez.core.api.impl.graph.DefaultBounds;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class BPMNDiagramBuilder extends AbstractObjectBuilder<BPMNDiagram, DefaultGraph<BPMNDiagram, DefaultNode, DefaultEdge>> {

    public BPMNDiagramBuilder(BPMNGraphObjectBuilderFactory wiresFactory) {
        super(wiresFactory);
    }

    @Override
    public DefaultGraph<BPMNDiagram, DefaultNode, DefaultEdge> build(BuilderContext context) {
        // TODO: properties.
        final Map<String, Object> properties = new HashMap<String, Object>();
        
        // TODO: bounds.
        final Element.Bounds bounds = 
                new DefaultBounds(
                        new DefaultBound(100d, 100d),
                        new DefaultBound(100d, 100d)
                );


        DefaultGraph<BPMNDiagram, DefaultNode, DefaultEdge> result = BPMNDiagram.INSTANCE.build(nodeId, properties, bounds);
        context.init(result);
        
        StartNoneEventBuilder startProcessNodeBuilder = getStartProcessNode(context);
        if (startProcessNodeBuilder == null) {
            throw new RuntimeException("No start process event found!");
        }

        DefaultNode startProcessNode = startProcessNodeBuilder.build(context);
        result.addNode(startProcessNode);

        return result;
    }
    
    // TODO: Can be multiple
    protected StartNoneEventBuilder getStartProcessNode(BuilderContext context) {
        Collection<GraphObjectBuilder<?, ?>> builders = context.getBuilders();
        if (builders != null && !builders.isEmpty()) {
            for (GraphObjectBuilder<?, ?> builder : builders) {
                try {
                    return (StartNoneEventBuilder) builder;
                } catch (ClassCastException e) {
                    // Not a start event. Continue with the search...
                }
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "[NodeBuilder=BPMNDiagramBuilder]" + super.toString();
    }
}
