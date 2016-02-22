package org.wirez.client.views;

import com.ait.lienzo.client.core.shape.MultiPath;
import com.ait.lienzo.client.core.shape.RegularPolygon;
import com.ait.lienzo.client.core.shape.Shape;
import com.ait.lienzo.client.core.shape.wires.WiresLayoutContainer;
import com.ait.lienzo.client.core.shape.wires.WiresManager;
import com.ait.lienzo.shared.core.types.ColorName;
import com.google.gwt.event.shared.HandlerRegistration;
import org.wirez.core.client.view.HasRadius;
import org.wirez.core.client.view.event.ViewEvent;
import org.wirez.core.client.view.event.ViewEventType;
import org.wirez.core.client.view.event.ViewHandler;

public class WiresPolygonView extends AbstractWiresShapeView<WiresPolygonView> 
        implements HasRadius<WiresPolygonView> {

    protected RegularPolygon decorator;

    public WiresPolygonView(final double radius,
                            final String fillColor,
                            final WiresManager manager) {
        super(new MultiPath()
                        .M(0 ,radius)
                        .L(radius, 0)
                        .L(radius * 2, radius)
                        .L(radius, ( radius * 2) )
                        .Z()
                        .setStrokeColor(ColorName.BLACK)
                        .setFillColor(fillColor),
                manager);

        init(radius);
    }

    protected void init(final double radius) {
        decorator = new RegularPolygon(4, radius)
                .setStrokeWidth(0)
                .setStrokeAlpha(0)
                .setFillAlpha(0)
                .setStrokeAlpha(0);
        this.addChild(decorator, WiresLayoutContainer.Layout.CENTER);
    }

    @Override
    protected WiresLayoutContainer.Layout getTextPosition() {
        return WiresLayoutContainer.Layout.BOTTOM;
    }
    
    @Override
    protected Shape getDecorator() {
        return decorator;
    }
    @Override
    public boolean supports(final ViewEventType type) {
        return ViewEventType.MOUSE_CLICK.equals( type );
    }

    @Override
    protected HandlerRegistration doAddHandler(final ViewEventType type, 
                                               final ViewHandler<ViewEvent> eventHandler) {
        
        if ( ViewEventType.MOUSE_CLICK.equals(type) ) {
            return registerClickHandler(getPath(), eventHandler);
        }

        return null;
    }

    @Override
    public WiresPolygonView setRadius(final double radius) {
        if (radius > 0) {
            decorator.setRadius(radius);
        }
        return this;
    }
}
