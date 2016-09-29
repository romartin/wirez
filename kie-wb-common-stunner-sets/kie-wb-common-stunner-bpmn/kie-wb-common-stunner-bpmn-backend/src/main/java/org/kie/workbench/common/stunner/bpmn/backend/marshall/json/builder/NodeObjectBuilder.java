package org.kie.workbench.common.stunner.bpmn.backend.marshall.json.builder;

import org.kie.workbench.common.stunner.core.graph.Edge;
import org.kie.workbench.common.stunner.core.graph.Node;
import org.kie.workbench.common.stunner.core.graph.content.view.View;

public interface NodeObjectBuilder<W, T extends Node<View<W>, Edge>> 
        extends GraphObjectBuilder<W, T> {

    Class<?> getDefinitionClass();

    NodeObjectBuilder<W, T> child(String nodeId);

}
