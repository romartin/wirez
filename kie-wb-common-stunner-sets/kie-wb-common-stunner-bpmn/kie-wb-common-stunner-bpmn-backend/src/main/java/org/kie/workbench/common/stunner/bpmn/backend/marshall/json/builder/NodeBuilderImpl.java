package org.kie.workbench.common.stunner.bpmn.backend.marshall.json.builder;

import org.kie.workbench.common.stunner.bpmn.definition.BPMNDefinition;
import org.kie.workbench.common.stunner.core.graph.Edge;
import org.kie.workbench.common.stunner.core.graph.Node;
import org.kie.workbench.common.stunner.core.graph.content.view.View;

public class NodeBuilderImpl extends AbstractNodeBuilder<BPMNDefinition, Node<View<BPMNDefinition>, Edge>> {

    public NodeBuilderImpl(Class<?> definitionClass) {
        super(definitionClass);
    }
}
