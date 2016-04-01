package org.wirez.bpmn.backend.marshall.json.builder;

import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.content.view.View;

public interface NodeObjectBuilder<W, T extends Node<View<W>, Edge>> 
        extends GraphObjectBuilder<W, T> {

    NodeObjectBuilder<W, T> child(String nodeId);

}
