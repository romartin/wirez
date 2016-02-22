package org.wirez.client.views;

import com.ait.lienzo.client.core.event.NodeMouseClickEvent;
import com.ait.lienzo.client.core.event.NodeMouseClickHandler;
import com.ait.lienzo.client.core.event.NodeMouseMoveEvent;
import com.ait.lienzo.client.core.event.NodeMouseMoveHandler;
import com.ait.lienzo.client.core.shape.IPrimitive;
import com.ait.tooling.nativetools.client.event.HandlerRegistrationManager;
import com.google.gwt.event.shared.HandlerRegistration;
import org.wirez.core.client.canvas.Layer;
import org.wirez.core.client.view.ShapeView;
import org.wirez.core.client.view.event.*;

import javax.enterprise.context.Dependent;
import javax.inject.Named;
import java.util.HashMap;
import java.util.Map;

@Dependent
@Named( "wires" )
public class WiresLayer implements Layer<WiresLayer, ShapeView<?>> {

    protected final HandlerRegistrationManager registrationManager = new HandlerRegistrationManager();
    protected final Map<ViewEventType, HandlerRegistration> registrationMap = new HashMap<>();
    protected com.ait.lienzo.client.core.shape.Layer layer;

    public WiresLayer() {
        
    }

    @Override
    public WiresLayer initialize(final Object view) {
        this.layer = (com.ait.lienzo.client.core.shape.Layer) view;
        return this;
    }

    @Override
    public WiresLayer addShape(final ShapeView<?> shape) {
        layer.add((IPrimitive<?>) shape);
        return this;
    }

    @Override
    public WiresLayer removeShape(final ShapeView<?> shape) {
        layer.remove((IPrimitive<?>) shape);
        return this;
    }

    @Override
    public WiresLayer draw() {
        layer.batch();
        return this;
    }

    @Override
    public boolean supports(final ViewEventType type) {
        return ViewEventType.MOUSE_CLICK.equals( type ) || ViewEventType.MOUSE_MOVE.equals( type );
    }

    @Override
    public WiresLayer addHandler(final ViewEventType type,
                                 final ViewHandler<ViewEvent> eventHandler) {

        HandlerRegistration registration = null;
        
        if ( ViewEventType.MOUSE_CLICK.equals( type ) ) {
            
            registration = registerClickHandler(eventHandler);
            
        } else if ( ViewEventType.MOUSE_MOVE.equals( type ) ) {
         
            registration = registerMouseMoveHandler(eventHandler);
        }
        
        if ( null != registration ) {
            registrationMap.put(type, registration);
            registrationManager.register(registration);
        }
        
        return this;
    }

    @Override
    public WiresLayer removeHandler(final ViewHandler<ViewEvent> eventHandler) {
        final ViewEventType type = eventHandler.getType();
        if ( registrationMap.containsKey( type ) ) {
            final HandlerRegistration registration = registrationMap.get( type );
            registrationManager.isRegistered(registration);
        }
        return this;
    }

    protected HandlerRegistration registerClickHandler(final ViewHandler<ViewEvent> eventHandler) {
        return layer.addNodeMouseClickHandler(new NodeMouseClickHandler() {
            @Override
            public void onNodeMouseClick(final NodeMouseClickEvent nodeMouseClickEvent) {
                final MouseClickEvent event = new MouseClickEvent(nodeMouseClickEvent.getX(), nodeMouseClickEvent.getY());
                eventHandler.handle( event );
            }
        });
    }

    protected HandlerRegistration registerMouseMoveHandler(final ViewHandler<ViewEvent> eventHandler) {
        
        return layer.addNodeMouseMoveHandler(new NodeMouseMoveHandler() {
            @Override
            public void onNodeMouseMove(final NodeMouseMoveEvent nodeMouseMoveEvent) {
                final MouseMoveEvent event = new MouseMoveEvent(nodeMouseMoveEvent.getX(), nodeMouseMoveEvent.getX(), nodeMouseMoveEvent.isShiftKeyDown());
                eventHandler.handle( event );
            }
        });
    }
    
}
