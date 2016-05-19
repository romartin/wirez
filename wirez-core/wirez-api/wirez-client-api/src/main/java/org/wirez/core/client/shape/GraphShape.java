package org.wirez.core.client.shape;

import org.wirez.core.graph.Element;
import org.wirez.core.graph.content.view.View;
import org.wirez.core.client.shape.view.ShapeView;

/**
 * A shape mutates according to a graph element.
 */
public interface GraphShape<W, C extends View<W>, E extends Element<C>, V extends ShapeView> extends org.wirez.core.client.shape.MutableShape<E, V> {

    void applyPosition(E element, org.wirez.core.client.shape.MutationContext mutationContext);

}
