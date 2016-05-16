package org.wirez.client.shapes.view;

import com.ait.lienzo.client.core.shape.OrthogonalPolyLine;
import com.ait.lienzo.client.core.shape.SimpleArrow;
import com.ait.lienzo.client.core.shape.wires.WiresManager;
import com.ait.lienzo.client.core.types.Point2DArray;
import com.google.gwt.event.shared.HandlerRegistration;
import org.wirez.client.shapes.view.animatiion.AnimatedWiresConnectorView;
import org.wirez.core.client.shape.view.event.ViewEvent;
import org.wirez.core.client.shape.view.event.ViewEventType;
import org.wirez.core.client.shape.view.event.ViewHandler;

public class ConnectorView<T extends ConnectorView> extends AnimatedWiresConnectorView<T> {

    
    public ConnectorView(final WiresManager manager,
                         final double... points) {
        super(createLine(points), null, new SimpleArrow(20, 0.75), manager);
    }

    @Override
    protected HandlerRegistration doAddHandler(final ViewEventType type, final ViewHandler<ViewEvent> eventHandler) {
        if ( ViewEventType.MOUSE_CLICK.equals( type ) ) {
            return registerClickHandler(getDecoratableLine(), eventHandler);
        }
        
        return null;
    }

    @Override
    public boolean supports(final ViewEventType type) {
        return ViewEventType.MOUSE_CLICK.equals( type );
    }

    private static final OrthogonalPolyLine createLine(final double... points)
    {
        return new OrthogonalPolyLine(Point2DArray.fromArrayOfDouble(points)).setCornerRadius(5).setDraggable(true);
    }
}
