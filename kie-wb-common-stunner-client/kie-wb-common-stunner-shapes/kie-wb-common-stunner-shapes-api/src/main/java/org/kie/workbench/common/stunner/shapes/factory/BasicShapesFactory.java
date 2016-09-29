package org.kie.workbench.common.stunner.shapes.factory;

import org.kie.workbench.common.stunner.core.client.canvas.CanvasHandler;
import org.kie.workbench.common.stunner.core.client.shape.MutableShape;
import org.kie.workbench.common.stunner.core.client.shape.factory.ShapeProxyFactory;
import org.kie.workbench.common.stunner.core.client.shape.view.ShapeView;
import org.kie.workbench.common.stunner.shapes.proxy.BasicShapeProxy;

public interface BasicShapesFactory<W, H extends CanvasHandler>
    extends ShapeProxyFactory<W, H, MutableShape<W, ShapeView>, BasicShapeProxy<W>> {
}
