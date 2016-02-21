package org.wirez.client.views.primitives;

import com.ait.lienzo.client.core.event.NodeMouseClickEvent;
import com.ait.lienzo.client.core.event.NodeMouseClickHandler;
import com.ait.lienzo.client.core.shape.Rectangle;
import com.google.gwt.event.shared.HandlerRegistration;
import org.wirez.core.client.view.event.ClickEvent;
import org.wirez.core.client.view.event.ViewEvent;
import org.wirez.core.client.view.event.ViewEventType;
import org.wirez.core.client.view.event.ViewHandler;
import org.wirez.core.client.view.HasSize;

public class RectangleView extends AbstractPrimitiveView<RectangleView> implements HasSize<RectangleView> {

    private final Rectangle rectangle;
    
    public RectangleView() {
        this.rectangle = new Rectangle(50, 50);
    }

    public RectangleView(final double width, final double height) {
        this.rectangle = new Rectangle(width, height);
    }

    @Override
    public double getShapeX() {
        return rectangle.getX();
    }

    @Override
    public double getShapeY() {
        return rectangle.getY();
    }

    @Override
    public RectangleView setShapeX(final double x) {
        rectangle.setX(x);
        return this;
    }

    @Override
    public RectangleView setShapeY(final double y) {
        rectangle.setY(y);
        return this;
    }

    @Override
    public String getFillColor() {
        return rectangle.getFillColor();
    }

    @Override
    public RectangleView setFillColor(final String color) {
        rectangle.setFillColor(color);
        return this;
    }

    @Override
    public double getFillAlpha() {
        return rectangle.getFillAlpha();
    }

    @Override
    public RectangleView setFillAlpha(final double alpha) {
        rectangle.setFillAlpha(alpha);
        return this;
    }

    @Override
    public String getStrokeColor() {
        return rectangle.getStrokeColor();
    }

    @Override
    public RectangleView setStrokeColor(final String color) {
        rectangle.setStrokeColor(color);
        return this;
    }

    @Override
    public double getStrokeAlpha() {
        return rectangle.getStrokeAlpha();
    }

    @Override
    public RectangleView setStrokeAlpha(final double alpha) {
        rectangle.setStrokeAlpha(alpha);
        return this;
    }

    @Override
    public double getStrokeWidth() {
        return rectangle.getStrokeWidth();
    }

    @Override
    public RectangleView setStrokeWidth(final double width) {
        rectangle.setStrokeWidth(width);
        return this;
    }

    @Override
    public RectangleView moveToTop() {
        rectangle.moveToTop();
        return this;
    }

    @Override
    public RectangleView moveToBottom() {
        rectangle.moveToBottom();
        return this;
    }

    @Override
    public RectangleView moveUp() {
        rectangle.moveUp();
        return this;
    }

    @Override
    public RectangleView moveDown() {
        rectangle.moveDown();
        return this;
    }

    @Override
    public void removeFromParent() {
        rectangle.removeFromParent();
    }

    @Override
    public RectangleView setSize(final double width, final double height) {
        rectangle.setWidth(width);
        rectangle.setHeight(height);
        return this;
    }

    @Override
    public boolean supports(final ViewEventType type) {
        return ViewEventType.CLICK.equals(type);
    }

    protected HandlerRegistration doAddHandler(final ViewEventType type, 
                                               final ViewHandler<ViewEvent> eventHandler) {
        assert ViewEventType.CLICK.equals(type);
        
        return rectangle.addNodeMouseClickHandler(new NodeMouseClickHandler() {
            @Override
            public void onNodeMouseClick(final NodeMouseClickEvent nodeMouseClickEvent) {
                final ClickEvent event = new ClickEvent(nodeMouseClickEvent.getX(), nodeMouseClickEvent.getY());
                eventHandler.handle( event );
            }
        });
        
    }

}
