package org.wirez.client.views.primitives;

import com.ait.lienzo.client.core.event.NodeMouseClickEvent;
import com.ait.lienzo.client.core.event.NodeMouseClickHandler;
import com.ait.lienzo.client.core.shape.Circle;
import com.google.gwt.event.shared.HandlerRegistration;
import org.wirez.core.client.view.event.ClickEvent;
import org.wirez.core.client.view.event.ViewEvent;
import org.wirez.core.client.view.event.ViewEventType;
import org.wirez.core.client.view.event.ViewHandler;
import org.wirez.core.client.view.HasRadius;

public class CircleView extends AbstractPrimitiveView<CircleView> implements HasRadius<CircleView> {

    private final Circle circle;
    
    public CircleView() {
        this.circle = new Circle(25);
    }

    public CircleView(final double radius) {
        this.circle = new Circle(radius);
    }

    @Override
    public double getShapeX() {
        return circle.getX();
    }

    @Override
    public double getShapeY() {
        return circle.getY();
    }

    @Override
    public CircleView setShapeX(final double x) {
        this.circle.setX(x);
        return this;
    }

    @Override
    public CircleView setShapeY(final double y) {
        circle.setY(y);
        return this;
    }

    @Override
    public String getFillColor() {
        return circle.getFillColor();
    }

    @Override
    public CircleView setFillColor(final String color) {
        circle.setFillColor(color);
        return this;
    }

    @Override
    public double getFillAlpha() {
        return circle.getFillAlpha();
    }

    @Override
    public CircleView setFillAlpha(final double alpha) {
        circle.setFillAlpha(alpha);
        return this;
    }

    @Override
    public String getStrokeColor() {
        return circle.getStrokeColor();
    }

    @Override
    public CircleView setStrokeColor(final String color) {
        circle.setStrokeColor(color);
        return this;
    }

    @Override
    public double getStrokeAlpha() {
        return circle.getStrokeAlpha();
    }

    @Override
    public CircleView setStrokeAlpha(final double alpha) {
        circle.setStrokeAlpha(alpha);
        return this;
    }

    @Override
    public double getStrokeWidth() {
        return circle.getStrokeWidth();
    }

    @Override
    public CircleView setStrokeWidth(final double width) {
        circle.setStrokeWidth(width);
        return this;
    }

    @Override
    public CircleView moveToTop() {
        circle.moveToTop();
        return this;
    }

    @Override
    public CircleView moveToBottom() {
        circle.moveToBottom();
        return this;
    }

    @Override
    public CircleView moveUp() {
        circle.moveUp();
        return this;
    }

    @Override
    public CircleView moveDown() {
        circle.moveDown();
        return this;
    }

    @Override
    public void removeFromParent() {
        circle.removeFromParent();
    }

    @Override
    public CircleView setRadius(final double radius) {
        circle.setRadius(radius);
        return this;
    }

    @Override
    public boolean supports(final ViewEventType type) {
        return ViewEventType.CLICK.equals(type);
    }

    protected HandlerRegistration doAddHandler(final ViewEventType type,
                                               final ViewHandler<ViewEvent> eventHandler) {
        assert ViewEventType.CLICK.equals(type);

        return circle.addNodeMouseClickHandler(new NodeMouseClickHandler() {
            @Override
            public void onNodeMouseClick(final NodeMouseClickEvent nodeMouseClickEvent) {
                final ClickEvent event = new ClickEvent(nodeMouseClickEvent.getX(), nodeMouseClickEvent.getY());
                eventHandler.handle( event );
            }
        });

    }
    
}
