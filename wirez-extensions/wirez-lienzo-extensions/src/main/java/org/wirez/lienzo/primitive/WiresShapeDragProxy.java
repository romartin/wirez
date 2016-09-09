package org.wirez.lienzo.primitive;

import com.ait.lienzo.client.core.shape.Layer;
import com.ait.lienzo.client.core.shape.wires.WiresManager;
import com.ait.lienzo.client.core.shape.wires.WiresShape;

public class WiresShapeDragProxy extends AbstractDragProxy<WiresShape> {

    public WiresShapeDragProxy(final Layer layer,
                               final WiresShape shape,
                               final int x,
                               final int y,
                               final int timeout,
                               final Callback callback) {

        super(layer, shape, x, y, timeout, callback);

    }
    
    @Override
    protected void addToLayer(final Layer layer, final WiresShape shape) {
        getWiresManager( layer ).register( shape );
        getWiresManager( layer ).getMagnetManager().createMagnets( shape );
    }

    @Override
    protected void removeFromLayer(final Layer layer, final WiresShape shape) {
        getWiresManager( layer ).deregister( shape );
    }

    @Override
    protected void setX(final WiresShape shape, final int x) {
        shape.setX( x );
    }

    @Override
    protected void setY(final WiresShape shape, final int y) {
        shape.setY( y );
    }
    
    protected WiresManager getWiresManager( final Layer layer ) {
        return WiresManager.get( layer );
    }
    
}