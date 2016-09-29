package org.kie.workbench.common.stunner.core.client.shape;

import org.kie.workbench.common.stunner.core.graph.Edge;
import org.kie.workbench.common.stunner.core.graph.Node;
import org.kie.workbench.common.stunner.core.graph.content.view.View;
import org.kie.workbench.common.stunner.core.client.shape.view.ShapeView;

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
