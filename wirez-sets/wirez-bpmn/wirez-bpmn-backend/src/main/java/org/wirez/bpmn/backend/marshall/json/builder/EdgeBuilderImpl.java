package org.wirez.bpmn.backend.marshall.json.builder;

import org.wirez.bpmn.api.BPMNDefinition;
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.content.view.View;

public final class EdgeBuilderImpl extends AbstractEdgeBuilder<BPMNDefinition, Edge<View<BPMNDefinition>, Node>> {
    
    public EdgeBuilderImpl(Class<?> definitionClass) {
        super(definitionClass);
    }
}
