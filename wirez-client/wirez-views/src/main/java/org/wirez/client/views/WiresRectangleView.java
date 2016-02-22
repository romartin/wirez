package org.wirez.client.views;

import com.ait.lienzo.client.core.shape.MultiPath;
import com.ait.lienzo.client.core.shape.Rectangle;
import com.ait.lienzo.client.core.shape.Shape;
import com.ait.lienzo.client.core.shape.wires.WiresLayoutContainer;
import com.ait.lienzo.client.core.shape.wires.WiresManager;
import com.google.gwt.event.shared.HandlerRegistration;
import org.wirez.core.client.view.HasSize;
import org.wirez.core.client.view.event.DragHandler;
import org.wirez.core.client.view.event.ViewEvent;
import org.wirez.core.client.view.event.ViewEventType;
import org.wirez.core.client.view.event.ViewHandler;

public class WiresRectangleView extends AbstractWiresShapeView<WiresRectangleView> 
        implements HasSize<WiresRectangleView> {

    private Rectangle decorator;
    
    public WiresRectangleView(final double width,
                              final double height,
                              final WiresManager manager) {
        super(new MultiPath().rect(0, 0, width, height), manager);
        
        init(width, height);
    }

    @Override
    protected Shape getDecorator() {
        return decorator;
    }
    
    private void init(final double width,
                      final double height) {
        decorator = new Rectangle(width, height).setX(0).setY(0).setFillAlpha(0).setStrokeAlpha(0);
        this.addChild(decorator, WiresLayoutContainer.Layout.CENTER,
                getDecoratorCoordinate( width ),
                getDecoratorCoordinate( height ) );
    }

    @Override
    public boolean supports(final ViewEventType type) {
        return ViewEventType.MOUSE_CLICK.equals( type ) || ViewEventType.DRAG.equals( type );
    }

    @Override
    protected HandlerRegistration doAddHandler(final ViewEventType type, 
                                               final ViewHandler<? extends ViewEvent> eventHandler) {
        
        if ( ViewEventType.MOUSE_CLICK.equals(type) ) {
            return registerClickHandler(getPath(), (ViewHandler<ViewEvent>) eventHandler);
        }

        if ( ViewEventType.DRAG.equals(type) ) {
            return registerDragHandler(getPath(), (DragHandler) eventHandler);
        }

        return null;
    }
    
    @Override
    public WiresRectangleView setSize(final double width, final double height) {
        final double x = getPath().getX();
        final double y = getPath().getY();
        getPath().clear().rect(x, y, width, height);
        decorator.setWidth(width);
        decorator.setHeight(height);
        this.moveChild(decorator, getDecoratorCoordinate(width), getDecoratorCoordinate(height));
        return this;
    }


    private double getDecoratorCoordinate(final double c) {
        return - ( c / 2 );
    }

}
