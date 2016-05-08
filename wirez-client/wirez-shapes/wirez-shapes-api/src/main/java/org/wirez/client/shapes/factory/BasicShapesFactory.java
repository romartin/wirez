package org.wirez.client.shapes.factory;

import org.wirez.client.shapes.proxy.BasicShapeProxy;
import org.wirez.core.client.canvas.CanvasHandler;
import org.wirez.core.client.shape.MutableShape;
import org.wirez.core.client.shape.factory.ShapeProxyFactory;
import org.wirez.core.client.shape.view.ShapeView;

public interface BasicShapesFactory<W, H extends CanvasHandler>
    extends ShapeProxyFactory<W, H, MutableShape<W, ShapeView>, BasicShapeProxy<W>> {
}
