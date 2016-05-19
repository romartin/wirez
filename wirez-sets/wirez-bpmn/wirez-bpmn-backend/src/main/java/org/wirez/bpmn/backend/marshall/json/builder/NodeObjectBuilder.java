package org.wirez.bpmn.backend.marshall.json.builder;

import org.wirez.core.graph.Edge;
import org.wirez.core.graph.Node;
import org.wirez.core.graph.content.view.View;

public interface NodeObjectBuilder<W, T extends Node<View<W>, Edge>> 
        extends GraphObjectBuilder<W, T> {

    Class<?> getDefinitionClass();

    NodeObjectBuilder<W, T> child(String nodeId);

}
