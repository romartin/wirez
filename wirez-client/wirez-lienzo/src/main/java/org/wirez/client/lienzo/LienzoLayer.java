package org.wirez.client.lienzo;

import com.ait.lienzo.client.core.event.NodeMouseClickEvent;
import com.ait.lienzo.client.core.event.NodeMouseClickHandler;
import com.ait.lienzo.client.core.event.NodeMouseMoveEvent;
import com.ait.lienzo.client.core.event.NodeMouseMoveHandler;
import com.ait.lienzo.client.core.shape.IPrimitive;
import com.ait.lienzo.client.core.shape.Shape;
import com.ait.lienzo.shared.core.types.DataURLType;
import com.ait.tooling.nativetools.client.event.HandlerRegistrationManager;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerRegistration;
import org.uberfire.mvp.Command;
import org.wirez.core.client.canvas.Layer;
import org.wirez.core.client.shape.view.ShapeView;
import org.wirez.core.client.shape.view.event.*;

import javax.enterprise.context.Dependent;
import java.util.HashMap;
import java.util.Map;

@Dependent
@Lienzo
public class LienzoLayer implements Layer<LienzoLayer, ShapeView<?>, Shape<?>> {

    protected final HandlerRegistrationManager registrationManager = new HandlerRegistrationManager();
    protected final Map<ViewEventType, HandlerRegistration> registrationMap = new HashMap<>();
    protected com.ait.lienzo.client.core.shape.Layer layer;

    public LienzoLayer() {
        
    }

    @Override
    public LienzoLayer initialize(final Object view) {
        this.layer = (com.ait.lienzo.client.core.shape.Layer) view;
        return this;
    }

    @Override
    public LienzoLayer addShape(final ShapeView<?> shape) {
        GWT.log("Adding shape " + shape.toString());
        layer.add((IPrimitive<?>) shape);
        return this;
    }

    @Override
    public LienzoLayer removeShape(final ShapeView<?> shape) {
        GWT.log("Removing shape " + shape.toString());
        layer.remove((IPrimitive<?>) shape);
        return this;
    }

    @Override
    public LienzoLayer draw() {
        layer.batch();
        
        return this;
    }

    @Override
    public void clear() {
        layer.clear();
    }

    @Override
    public String toDataURL() {
        return layer.toDataURL(DataURLType.PNG);
    }

    @Override
    public void onBeforeDraw(final Command callback) {
        layer.setOnLayerBeforeDraw(layer1 -> {
            callback.execute();
            return true;
        });
    }

    @Override
    public void onAfterDraw(final Command callback) {
        layer.setOnLayerAfterDraw(layer1 -> callback.execute());
    }

    @Override
    public boolean supports(final ViewEventType type) {
        return ViewEventType.MOUSE_CLICK.equals( type ) || ViewEventType.MOUSE_MOVE.equals( type );
    }

    @Override
    public LienzoLayer addHandler(final ViewEventType type,
                                  final ViewHandler<? extends ViewEvent> eventHandler) {

        HandlerRegistration registration = null;
        
        if ( ViewEventType.MOUSE_CLICK.equals( type ) ) {
            
            registration = registerClickHandler((ViewHandler<ViewEvent>) eventHandler);
            
        } else if ( ViewEventType.MOUSE_MOVE.equals( type ) ) {
         
            registration = registerMouseMoveHandler((ViewHandler<ViewEvent>) eventHandler);
        }
        
        if ( null != registration ) {
            registrationMap.put(type, registration);
            registrationManager.register(registration);
        }
        
        return this;
    }

    @Override
    public LienzoLayer removeHandler(final ViewHandler<? extends ViewEvent> eventHandler) {
        final ViewEventType type = eventHandler.getType();
        if ( registrationMap.containsKey( type ) ) {
            final HandlerRegistration registration = registrationMap.get( type );
            registrationManager.deregister(registration);
        }
        return this;
    }

    @Override
    public Shape<?> getAttachableShape() {
        return null;
    }

    protected HandlerRegistration registerClickHandler(final ViewHandler<ViewEvent> eventHandler) {
        return layer.addNodeMouseClickHandler(new NodeMouseClickHandler() {
            @Override
            public void onNodeMouseClick(final NodeMouseClickEvent nodeMouseClickEvent) {
                final MouseClickEvent event = new MouseClickEvent(nodeMouseClickEvent.getX(), nodeMouseClickEvent.getY(), nodeMouseClickEvent.isShiftKeyDown());
                eventHandler.handle( event );
            }
        });
    }

    protected HandlerRegistration registerMouseMoveHandler(final ViewHandler<ViewEvent> eventHandler) {
        
        return layer.addNodeMouseMoveHandler(new NodeMouseMoveHandler() {
            @Override
            public void onNodeMouseMove(final NodeMouseMoveEvent nodeMouseMoveEvent) {
                final MouseMoveEvent event = new MouseMoveEvent(nodeMouseMoveEvent.getX(), nodeMouseMoveEvent.getY(), nodeMouseMoveEvent.isShiftKeyDown());
                eventHandler.handle( event );
            }
        });
    }
    
    public com.ait.lienzo.client.core.shape.Layer getLienzoLayer() {
        return this.layer;
    }
}
