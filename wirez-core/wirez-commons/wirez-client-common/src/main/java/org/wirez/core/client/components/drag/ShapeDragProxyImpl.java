package org.wirez.core.client.components.drag;

import org.wirez.core.client.canvas.AbstractCanvas;
import org.wirez.core.client.shape.Shape;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class ShapeDragProxyImpl implements ShapeDragProxy<AbstractCanvas> {

    ShapeViewDragProxy<AbstractCanvas> shapeViewDragProxyFactory;
    
    @Inject
    public ShapeDragProxyImpl( final ShapeViewDragProxy<AbstractCanvas> shapeViewDragProxyFactory) {
        this.shapeViewDragProxyFactory = shapeViewDragProxyFactory;
    }

    @Override
    public DragProxy<AbstractCanvas, Shape<?>, DragProxyCallback> proxyFor( final AbstractCanvas context) {
        this.shapeViewDragProxyFactory.proxyFor( context );
        return this;
    }

    @Override
    public DragProxy<AbstractCanvas, Shape<?>, DragProxyCallback> show( final Shape<?> item,
                                                                        final int x,
                                                                        final int y,
                                                                        final DragProxyCallback callback) {

        this.shapeViewDragProxyFactory.show( item.getShapeView(), x, y, callback );
        return this;
        
    }

    @Override
    public void clear() {
        if ( null != this.shapeViewDragProxyFactory ) {
            this.shapeViewDragProxyFactory.clear();
        }
    }

    @Override
    public void destroy() {
        if ( null != this.shapeViewDragProxyFactory ) {
            this.shapeViewDragProxyFactory.destroy();
        }
        this.shapeViewDragProxyFactory = null;
    }

}
