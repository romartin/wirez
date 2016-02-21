package org.wirez.client.views;

import com.ait.lienzo.client.core.event.NodeMouseClickEvent;
import com.ait.lienzo.client.core.event.NodeMouseClickHandler;
import com.ait.lienzo.client.core.shape.*;
import com.ait.lienzo.client.core.shape.wires.LayoutContainer;
import com.ait.lienzo.client.core.shape.wires.WiresLayoutContainer;
import com.ait.lienzo.client.core.shape.wires.WiresManager;
import com.ait.lienzo.client.core.shape.wires.WiresShape;
import com.ait.tooling.nativetools.client.event.HandlerRegistrationManager;
import com.google.gwt.event.shared.HandlerRegistration;
import org.wirez.client.views.primitives.AbstractPrimitiveView;
import org.wirez.core.client.HasDecorators;
import org.wirez.core.client.view.HasEventHandlers;
import org.wirez.core.client.view.ShapeView;
import org.wirez.core.client.view.event.ClickEvent;
import org.wirez.core.client.view.event.ViewEvent;
import org.wirez.core.client.view.event.ViewEventType;
import org.wirez.core.client.view.event.ViewHandler;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public abstract class AbstractWiresShapeView<T> extends WiresShape implements ShapeView<T>, HasEventHandlers<T>, HasDecorators {

    protected final HandlerRegistrationManager registrationManager = new HandlerRegistrationManager();
    protected final Map<ViewEventType, HandlerRegistration> registrationMap = new HashMap<>();
    protected final Collection<Shape> decorators= new LinkedList<>();

    public AbstractWiresShapeView(final MultiPath path, 
                                  final WiresManager manager) {
        super(path, new WiresLayoutContainer(), manager);
        this.decorators.add(getDecorator());
    }

    protected abstract HandlerRegistration doAddHandler(final ViewEventType type,
                                                        final ViewHandler<ViewEvent> eventHandler);

    protected abstract Shape getDecorator();
    
    @Override
    public Collection<Shape> getDecorators() {
        return decorators;
    }
    
    protected HandlerRegistration registerClickHandler(final Node node,
                                                       final ViewHandler<ViewEvent> eventHandler) {
        return node.addNodeMouseClickHandler(new NodeMouseClickHandler() {
            @Override
            public void onNodeMouseClick(final NodeMouseClickEvent nodeMouseClickEvent) {
                final ClickEvent event = new ClickEvent(nodeMouseClickEvent.getX(), nodeMouseClickEvent.getY());
                eventHandler.handle( event );
            }
        });
    }
    
    @Override
    public double getShapeX() {
        return getContainer().getAttributes().getX();
    }

    @Override
    public double getShapeY() {
        return getContainer().getAttributes().getY();
    }

    @Override
    public T setShapeX(final double x) {
        getContainer().getAttributes().setX(x);
        return (T) this;
    }

    @Override
    public T setShapeY(final double y) {
        getContainer().getAttributes().setY(y);
        return (T) this;
    }

    @Override
    public String getFillColor() {
        return getPath().getFillColor();
    }

    @Override
    public T setFillColor(final String color) {
        getPath().setFillColor(color);
        return (T) this;
    }

    @Override
    public double getFillAlpha() {
        return getPath().getFillAlpha();
    }

    @Override
    public T setFillAlpha(final double alpha) {
        getPath().setFillAlpha(alpha);
        return (T) this;
    }

    @Override
    public String getStrokeColor() {
        return getPath().getStrokeColor();
    }

    @Override
    public T setStrokeColor(final String color) {
        getPath().setStrokeColor(color);
        return (T) this;
    }

    @Override
    public double getStrokeAlpha() {
        return getPath().getStrokeAlpha();
    }

    @Override
    public T setStrokeAlpha(final double alpha) {
        getPath().setStrokeAlpha(alpha);
        return (T) this;
    }

    @Override
    public double getStrokeWidth() {
        return getPath().getStrokeWidth();
    }

    @Override
    public T setStrokeWidth(final double width) {
        getPath().setStrokeWidth(width);
        return (T) this;
    }

    @Override
    public T moveToTop() {
        getContainer().moveToTop();
        return (T) this;
    }

    @Override
    public T moveToBottom() {
        getContainer().moveToBottom();
        return (T) this;
    }

    @Override
    public T moveUp() {
        getContainer().moveUp();
        return (T) this;
    }

    @Override
    public T moveDown() {
        getContainer().moveDown();
        return (T) this;
    }

    @Override
    public T addHandler(final ViewEventType type,
                        final ViewHandler<ViewEvent> eventHandler) {

        final HandlerRegistration registration = doAddHandler(type, eventHandler);
        if ( null != registration ) {
            registrationMap.put(type, registration);
            registrationManager.register(registration);
        }
        return (T) this;
    }

    @Override
    public T removeHandler(final ViewHandler<ViewEvent> eventHandler) {
        final ViewEventType type = eventHandler.getType();
        if ( registrationMap.containsKey( type ) ) {
            final HandlerRegistration registration = registrationMap.get( type );
            registrationManager.isRegistered(registration);
        }
        return (T) this;
    }
}
