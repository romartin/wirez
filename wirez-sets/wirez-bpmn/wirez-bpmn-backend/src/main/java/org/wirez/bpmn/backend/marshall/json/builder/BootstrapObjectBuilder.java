package org.wirez.bpmn.backend.marshall.json.builder;

import org.wirez.core.graph.Element;
import org.wirez.core.graph.content.view.View;

import java.util.Map;

public class BootstrapObjectBuilder<W, T extends Element<View<W>>> extends AbstractObjectBuilder<W, T> {

    BPMNGraphObjectBuilderFactory buildersFactory;
            
    public BootstrapObjectBuilder(BPMNGraphObjectBuilderFactory buildersFactory) {
        super();
        this.buildersFactory = buildersFactory;
    }

    @Override
    @SuppressWarnings("unchecked")
    public AbstractObjectBuilder<W, T> stencil(String oryxStencilId) {
        assert nodeId != null;
        
        AbstractObjectBuilder<W, T> builder = (AbstractObjectBuilder<W, T>) buildersFactory.builderFor( oryxStencilId );
        
        builder.nodeId(this.nodeId);
        
        if (!properties.isEmpty()) {
            for (Map.Entry<String, String> entry : this.properties.entrySet()) {
                builder.property(entry.getKey(), entry.getValue());
            }
        }
        
        if (!outgoingResourceIds.isEmpty()) {
            for (String outRefId : outgoingResourceIds) {
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
