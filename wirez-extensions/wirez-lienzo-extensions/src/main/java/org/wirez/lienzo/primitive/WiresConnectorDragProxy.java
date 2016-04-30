package org.wirez.lienzo.primitive;

import com.ait.lienzo.client.core.shape.Layer;
import com.ait.lienzo.client.core.shape.wires.WiresConnector;
import com.ait.lienzo.client.core.shape.wires.WiresManager;

public class WiresConnectorDragProxy extends AbstractDragProxy<WiresConnector> {

    public WiresConnectorDragProxy(final Layer layer,
                                   final WiresConnector shape,
                                   final int x,
                                   final int y,
                                   final int timeout,
                                   final Callback callback) {

        super(layer, shape, x, y, timeout, callback);

    }
    
    @Override
    protected void addToLayer(final Layer layer, final WiresConnector shape) {
        getWiresManager( layer ).registerConnector( shape );
    }

    @Override
    protected void removeFromLayer(final Layer layer, final WiresConnector shape) {
        getWiresManager( layer ).deregisterConnector( shape );
    }

    @Override
    protected void setX(final WiresConnector shape, final int x) {
        shape.getDecoratableLine().setX( x );
    }

    @Override
    protected void setY(final WiresConnector shape, final int y) {
        shape.getDecoratableLine().setY( y );
    }
    
    protected WiresManager getWiresManager( final Layer layer ) {
        return WiresManager.get( layer );
    }
    
}