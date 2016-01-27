package org.wirez.bpmn.backend.marshall.json.builder;

import org.wirez.core.api.definition.Definition;
import org.wirez.core.api.graph.Element;
import org.wirez.core.api.graph.content.ViewContent;

import java.util.Map;

public class BootstrapObjectBuilder<W extends Definition, T extends Element<ViewContent<W>>> extends AbstractObjectBuilder<W, T> {

    public BootstrapObjectBuilder(BPMNGraphObjectBuilderFactory wiresFactory) {
        super(wiresFactory);
    }

    @Override
    public AbstractObjectBuilder<W, T> stencil(String stencilId) {
        assert nodeId != null;
        AbstractObjectBuilder<W, T> builder = (AbstractObjectBuilder<W, T>) bpmnGraphFactory.builderFor(stencilId);
        
        builder.nodeId(this.nodeId);
        
        if (!properties.isEmpty()) {
            for (Map.Entry<String, String> entry : this.properties.entrySet()) {
                builder.property(entry.getKey(), entry.getValue());
            }
        }
        
        if (!outgoingNodeIds.isEmpty()) {
            for (String outRefId : outgoingNodeIds) {
                builder.out(outRefId);
            }
        }
        return builder;
    }

    @Override
    public T build(BuilderContext context) {
        return null;
    }
}
