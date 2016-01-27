package org.wirez.bpmn.backend.marshall.json.builder;

import org.uberfire.ext.wirez.bpmn.api.BPMNWirez;
import org.uberfire.ext.wirez.core.api.graph.Element;

import java.util.Map;

public class BootstrapObjectBuilder<W extends BPMNWirez, T extends Element<W>> extends AbstractObjectBuilder<W, T> {

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
