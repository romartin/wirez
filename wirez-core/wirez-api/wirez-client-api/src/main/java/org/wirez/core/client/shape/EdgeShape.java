package org.wirez.core.client.shape;

import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.content.view.View;
import org.wirez.core.client.shape.view.ShapeView;

/**
 * A shape mutates according to a graph edge.
 */
public interface EdgeShape<W, C extends View<W>, E extends Edge<C, Node>, V extends ShapeView> 
        extends GraphShape<W, C, E, V> {

    void applyConnections(E element,
                          ShapeView<?> source,
                          ShapeView<?> target,
                          MutationContext mutationContext);

}
