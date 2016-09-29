package org.kie.workbench.common.stunner.core.client.shape;

import org.kie.workbench.common.stunner.core.client.shape.view.ShapeView;

/**
 * A shape that mutates according to a domain object.
 */
public interface MutableShape<W, V extends ShapeView> extends Shape<V> {

    void applyProperties(W element, MutationContext mutationContext);

    void applyProperty(W element,
                       String propertyId,
                       Object value,
                       MutationContext mutationContext);
    
}
