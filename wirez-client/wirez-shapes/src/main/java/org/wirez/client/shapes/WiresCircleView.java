package org.wirez.client.shapes;

import com.ait.lienzo.client.core.shape.Circle;
import com.ait.lienzo.client.core.shape.MultiPath;
import com.ait.lienzo.client.core.shape.Shape;
import com.ait.lienzo.client.core.shape.wires.WiresLayoutContainer;
import com.ait.lienzo.client.core.shape.wires.WiresManager;
import com.google.gwt.event.shared.HandlerRegistration;
import org.wirez.core.client.shape.view.HasRadius;
import org.wirez.core.client.shape.view.event.DragHandler;
import org.wirez.core.client.shape.view.event.ViewEvent;
import org.wirez.core.client.shape.view.event.ViewEventType;
import org.wirez.core.client.shape.view.event.ViewHandler;

public class WiresCircleView<T extends WiresCircleView> extends AbstractWiresShapeView<T> 
        implements HasRadius<T> {

    protected Circle circle;
    protected Circle decorator;

    public WiresCircleView(final double radius, 
                           final WiresManager manager) {
        super(new MultiPath().rect(0,0, radius * 2, radius * 2)
                        .setFillAlpha(1)
                        .setStrokeAlpha(0),
                manager);
        
        init(radius);
    }

    protected void init(final double radius) {
        circle = new Circle(radius).setX(radius).setY(radius);
        this.addChild(circle, WiresLayoutContainer.Layout.CENTER);
        decorator = new Circle(radius).setX(radius).setY(radius).setFillAlpha(0).setStrokeAlpha(0);
        this.addChild(decorator, WiresLayoutContainer.Layout.CENTER);
        // Path must be on top so different handlers ( such as the one in hover toolbox ) are attaching event handlers to it.
        getPath().moveToTop();
    }

    @Override
    protected Shape getShape() {
        return circle;
    }

    @Override
    protected Shape getDecorator() {
        return decorator;
    }
    @Override
    public boolean supports(final ViewEventType type) {
        return ViewEventType.MOUSE_CLICK.equals( type ) || ViewEventType.DRAG.equals( type );
    }

    @Override
    protected HandlerRegistration doAddHandler(final ViewEventType type, 
                                               final ViewHandler<? extends ViewEvent> eventHandler) {
        
        if ( ViewEventType.MOUSE_CLICK.equals(type) ) {
            return registerClickHandler(circle, (ViewHandler<ViewEvent>) eventHandler);
        }

        if ( ViewEventType.DRAG.equals(type) ) {
            return registerDragHandler(getPath(), (DragHandler) eventHandler);
        }

        return null;
    }

    @Override
    public T setRadius(final double radius) {
        if (radius > 0) {
            final double x = getPath().getX();
            final double y = getPath().getY();
            getPath().clear().rect(x, y, radius * 2, radius * 2);
            circle.setRadius(radius);
            decorator.setRadius(radius);
        }
        return (T) this;
    }
}
