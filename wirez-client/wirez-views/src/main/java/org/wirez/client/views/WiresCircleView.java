package org.wirez.client.views;

import com.ait.lienzo.client.core.shape.Circle;
import com.ait.lienzo.client.core.shape.MultiPath;
import com.ait.lienzo.client.core.shape.Shape;
import com.ait.lienzo.client.core.shape.wires.WiresLayoutContainer;
import com.ait.lienzo.client.core.shape.wires.WiresManager;
import com.google.gwt.event.shared.HandlerRegistration;
import org.wirez.core.client.view.HasRadius;
import org.wirez.core.client.view.event.ViewEvent;
import org.wirez.core.client.view.event.ViewEventType;
import org.wirez.core.client.view.event.ViewHandler;

public class WiresCircleView extends AbstractWiresShapeView<WiresCircleView> 
        implements HasRadius<WiresCircleView> {

    protected Circle circle;
    protected Circle decorator;

    public WiresCircleView(final double radius, 
                           final WiresManager manager) {
        super(new MultiPath().rect(0,0, radius, radius)
                        .setFillAlpha(0.001)
                        .setStrokeAlpha(0),
                manager);
        
        init(radius);
    }

    protected void init(final double radius) {
        circle = new Circle(radius).setX(radius).setY(radius);
        this.addChild(circle, WiresLayoutContainer.Layout.CENTER);
        decorator = new Circle(radius).setX(radius).setY(radius).setFillAlpha(0).setStrokeAlpha(0);
        this.addChild(decorator, WiresLayoutContainer.Layout.CENTER);
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
    
    

    private double getDecoratorCoordinate(final double c) {
        return - ( c / 2 );
    }

    @Override
    public WiresCircleView setRadius(final double radius) {
        if (radius > 0) {
            circle.setRadius(radius);
            decorator.setRadius(radius);
        }
        return this;
    }
}
