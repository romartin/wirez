package org.wirez.bpmn.backend.marshall.json.builder;

import org.wirez.core.api.definition.Definition;
import org.wirez.core.api.graph.Element;
import org.wirez.core.api.graph.content.ViewContent;

import java.util.*;

public abstract class AbstractObjectBuilder<W extends Definition, T extends Element<ViewContent<W>>> implements GraphObjectBuilder<W, T> {

    protected String nodeId;
    protected Map<String, String> properties;
    protected Set<String> outgoingNodeIds;
    protected BPMNGraphObjectBuilderFactory bpmnGraphFactory;
            
    public AbstractObjectBuilder(BPMNGraphObjectBuilderFactory bpmnGraphFactory) {
        this.bpmnGraphFactory = bpmnGraphFactory;
        properties = new HashMap<String, String>();
        outgoingNodeIds = new LinkedHashSet<String>();
    }

    @Override
    public GraphObjectBuilder<W, T> nodeId(String nodeId) {
        this.nodeId = nodeId;
        return this;
    }

    @Override
    public GraphObjectBuilder<W, T> property(String key, String value) {
        properties.put(key, value);
        return this;
    }

    @Override
    public GraphObjectBuilder<W, T> out(String nodeId) {
        outgoingNodeIds.add(nodeId);
        return this;
    }

    @Override
    public GraphObjectBuilder<W, T> stencil(String stencilId) {
        return this;
    }

    protected GraphObjectBuilder<?, ?> getBuilder(BuilderContext context, String nodeId) {
        Collection<GraphObjectBuilder<?, ?>> builders = context.getBuilders();
        if (builders != null && !builders.isEmpty()) {
            for (GraphObjectBuilder<?, ?> builder : builders) {
                AbstractObjectBuilder<?, ?> abstractBuilder = (AbstractObjectBuilder<?, ?>) builder;
                if (abstractBuilder.nodeId.equals(nodeId)) {
                    return builder;
                }
            }
        }
        return null;
    }
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("[NodeId=").append(nodeId).append("]");
        builder.append("[properties=").append(properties).append("]");
        builder.append("[outgoingNodeIds=").append(outgoingNodeIds).append("]");
        return builder.toString();
    }
}
