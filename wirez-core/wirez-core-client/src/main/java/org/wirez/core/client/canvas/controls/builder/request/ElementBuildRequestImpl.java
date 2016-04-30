package org.wirez.core.client.canvas.controls.builder.request;

import org.wirez.core.client.canvas.AbstractCanvasHandler;
import org.wirez.core.client.shape.factory.ShapeFactory;

public final class ElementBuildRequestImpl extends AbstractBuildRequest 
        implements ElementBuildRequest<AbstractCanvasHandler> {
    
    private final Object definition;
    private final ShapeFactory<?, AbstractCanvasHandler, ?> factory;

    @Override
    public Object getDefinition() {
        return definition;
    }

    @Override
    public ShapeFactory<?, AbstractCanvasHandler, ?> getShapeFactory() {
        return factory;
    }

    public ElementBuildRequestImpl(final double x,
                                   final double y,
                                   final Object definition,
                                   final ShapeFactory<?, AbstractCanvasHandler, ?> factory) {
        super(x, y);
        this.definition = definition;
        this.factory = factory;
    
    
    }
    
}
