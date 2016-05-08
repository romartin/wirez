package org.wirez.core.client.shape.factory;

import org.wirez.core.client.shape.Shape;

public interface CompositeShapeFactory<W, C, S extends Shape, F extends ShapeFactory<?, C, ?>> extends ShapeFactory<W, C, S> {

    void addFactory( F factory );
    
}
