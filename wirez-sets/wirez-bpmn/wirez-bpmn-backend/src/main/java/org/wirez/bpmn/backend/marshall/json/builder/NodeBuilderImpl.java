package org.wirez.bpmn.backend.marshall.json.builder;

import org.wirez.bpmn.definition.BPMNDefinition;
import org.wirez.core.graph.Edge;
import org.wirez.core.graph.Node;
import org.wirez.core.graph.content.view.View;

public class NodeBuilderImpl extends AbstractNodeBuilder<BPMNDefinition, Node<View<BPMNDefinition>, Edge>> {

    public NodeBuilderImpl(Class<?> definitionClass) {
        super(definitionClass);
    }
}
