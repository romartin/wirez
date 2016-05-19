package org.wirez.bpmn.backend.marshall.json.builder;

import org.wirez.bpmn.definition.BPMNDefinition;
import org.wirez.core.graph.Edge;
import org.wirez.core.graph.Node;
import org.wirez.core.graph.content.view.View;

public final class EdgeBuilderImpl extends AbstractEdgeBuilder<BPMNDefinition, Edge<View<BPMNDefinition>, Node>> {
    
    public EdgeBuilderImpl(Class<?> definitionClass) {
        super(definitionClass);
    }
}
