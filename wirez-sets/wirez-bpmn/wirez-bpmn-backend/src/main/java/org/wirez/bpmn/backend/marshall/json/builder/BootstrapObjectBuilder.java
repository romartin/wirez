package org.wirez.bpmn.backend.marshall.json.builder;

import org.wirez.core.api.definition.Definition;
import org.wirez.core.api.graph.Element;
import org.wirez.core.api.graph.content.view.View;

import java.util.Map;

public class BootstrapObjectBuilder<W extends Definition, T extends Element<View<W>>> extends AbstractObjectBuilder<W, T> {

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

        if ( null != boundLR ) {
            builder.boundLR( boundLR[0], boundLR[1]);
        }

        if ( null != boundUL ) {
            builder.boundUL( boundUL[0], boundUL[1]);
        }
        
        return builder;
    }

    @Override
    protected T doBuild(BuilderContext context) {
        return null;
    }

    @Override
    public T build(BuilderContext context) {
        return null;
    }
}
