package org.kie.workbench.common.stunner.core.client.shape.factory;

import org.kie.workbench.common.stunner.core.client.shape.Shape;

public interface CompositeShapeFactory<W, C, S extends Shape, F extends ShapeFactory<?, C, ?>> extends ShapeFactory<W, C, S> {

    void addFactory( F factory );
    
}
