package org.kie.workbench.common.stunner.core.client.shape;

import org.kie.workbench.common.stunner.core.graph.Edge;
import org.kie.workbench.common.stunner.core.graph.Node;
import org.kie.workbench.common.stunner.core.graph.content.view.View;
import org.kie.workbench.common.stunner.core.client.shape.view.ShapeView;

/**
 * A shape mutates according to a graph node.
 */
public interface NodeShape<W, C extends View<W>, E extends Node<C, Edge>, V extends ShapeView> extends GraphShape<W, C, E, V> {

}
