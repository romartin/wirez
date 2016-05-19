package org.wirez.core.client.components.drag;

import org.wirez.core.client.canvas.AbstractCanvas;
import org.wirez.core.client.shape.Shape;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class ShapeDragProxyFactoryImpl implements ShapeDragProxyFactory<AbstractCanvas> {

    ShapeViewDragProxyFactory<AbstractCanvas> shapeViewDragProxyFactory;
    
    @Inject
    public ShapeDragProxyFactoryImpl(final ShapeViewDragProxyFactory<AbstractCanvas> shapeViewDragProxyFactory) {
        this.shapeViewDragProxyFactory = shapeViewDragProxyFactory;
    }

    @Override
    public DragProxyFactory<AbstractCanvas, Shape<?>, DragProxyCallback> proxyFor(final AbstractCanvas context) {
        this.shapeViewDragProxyFactory.proxyFor( context );
        return this;
    }

    @Override
    public DragProxyFactory<AbstractCanvas, Shape<?>, DragProxyCallback> newInstance(final Shape<?> item,
                                                                               final int x,
                                                                               final int y,
                                                                               final DragProxyCallback callback) {

        this.shapeViewDragProxyFactory.newInstance( item.getShapeView(), x, y, callback );
        return this;
        
    }

    @Override
    public void destroy() {
        this.shapeViewDragProxyFactory.destroy();
        this.shapeViewDragProxyFactory = null;
    }

}
