package org.wirez.shapes.factory;

import org.wirez.core.client.canvas.CanvasHandler;
import org.wirez.core.client.shape.MutableShape;
import org.wirez.core.client.shape.factory.ShapeProxyFactory;
import org.wirez.core.client.shape.view.ShapeView;
import org.wirez.shapes.proxy.BasicShapeProxy;

public interface BasicShapesFactory<W, H extends CanvasHandler>
    extends ShapeProxyFactory<W, H, MutableShape<W, ShapeView>, BasicShapeProxy<W>> {
}
