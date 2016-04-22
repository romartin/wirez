package org.wirez.core.client.canvas.controls.builder;

import org.wirez.core.client.canvas.CanvasHandler;
import org.wirez.core.client.canvas.controls.CanvasControl;
import org.wirez.core.client.shape.factory.ShapeFactory;

public interface BuilderControl<C extends CanvasHandler> extends CanvasControl<C> {

    void build(Object definition,
               ShapeFactory factory,
               double _x,
               double _y);

}
