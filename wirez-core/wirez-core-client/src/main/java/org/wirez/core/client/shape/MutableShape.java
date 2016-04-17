package org.wirez.core.client.shape;

import org.wirez.core.api.graph.Element;
import org.wirez.core.api.graph.content.view.View;
import org.wirez.core.client.shape.view.ShapeView;

/**
 * A shape that could mutate when the underlying definition graph element has been updated.
 */
public interface MutableShape<W, C extends View<W>, E extends Element<C>, V extends ShapeView> extends Shape<V> {

    void applyPosition(E element);

    void applyProperties(E element);

    void applyProperty(E element, String propertyId, Object value);
    
}
