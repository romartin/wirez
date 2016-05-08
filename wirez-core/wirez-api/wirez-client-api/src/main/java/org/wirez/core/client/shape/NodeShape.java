package org.wirez.core.client.shape;

import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.content.view.View;
import org.wirez.core.client.shape.view.ShapeView;

/**
 * A shape mutates according to a graph node.
 */
public interface NodeShape<W, C extends View<W>, E extends Node<C, Edge>, V extends ShapeView> extends org.wirez.core.client.shape.GraphShape<W, C, E, V> {

}
