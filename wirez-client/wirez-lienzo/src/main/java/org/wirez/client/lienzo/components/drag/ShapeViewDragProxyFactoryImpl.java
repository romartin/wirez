package org.wirez.client.lienzo.components.drag;

import com.ait.lienzo.client.core.shape.wires.WiresConnector;
import com.ait.lienzo.client.core.shape.wires.WiresShape;
import org.wirez.client.lienzo.LienzoLayer;
import org.wirez.core.client.canvas.AbstractCanvas;
import org.wirez.core.client.components.drag.DragProxyCallback;
import org.wirez.core.client.components.drag.DragProxyFactory;
import org.wirez.core.client.components.drag.ShapeViewDragProxyFactory;
import org.wirez.core.client.shape.view.ShapeView;
import org.wirez.lienzo.primitive.AbstractDragProxy;
import org.wirez.lienzo.primitive.WiresConnectorDragProxy;
import org.wirez.lienzo.primitive.WiresShapeDragProxy;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class ShapeViewDragProxyFactoryImpl implements ShapeViewDragProxyFactory<AbstractCanvas> {

    private LienzoLayer layer;
    
    @Inject
    public ShapeViewDragProxyFactoryImpl() {
    }

    @Override
    public DragProxyFactory<AbstractCanvas, ShapeView<?>, DragProxyCallback> proxyFor(final AbstractCanvas context) {
        this.layer = (LienzoLayer) context.getLayer();
        return this;
    }

    @Override
    public DragProxyFactory<AbstractCanvas, ShapeView<?>, DragProxyCallback> newInstance(final ShapeView<?> item,
                                                                               final int x,
                                                                               final int y,
                                                                               final DragProxyCallback callback) {
        
        final AbstractDragProxy.Callback c = new AbstractDragProxy.Callback() {
            
            @Override
            public void onStart(final int x, 
                                final int y) {
                callback.onStart( x, y );
            }

            @Override
            public void onMove(final int x, final int y) {
                callback.onMove( x, y );
            }

            @Override
            public void onComplete(final int x, final int y) {
                callback.onComplete( x, y );
            }
        };
        
        if ( item instanceof WiresShape ) {

            final WiresShape wiresShape = (WiresShape) item;
            
            new WiresShapeDragProxy( layer.getLienzoLayer(), wiresShape, x, y, 200, c );
            
            
        } else if ( item instanceof WiresConnector) {
            
            final WiresConnector wiresConnector = (WiresConnector) item;
            
            new WiresConnectorDragProxy(layer.getLienzoLayer(), wiresConnector, x, y, 200, c);

        }
        
        return this;
    }

    @Override
    public void destroy() {
        this.layer = null;
    }

}
