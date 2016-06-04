package org.wirez.client.lienzo.shape.view;

import com.ait.lienzo.client.core.event.*;
import com.ait.lienzo.client.core.shape.Node;
import com.ait.tooling.nativetools.client.event.HandlerRegistrationManager;
import com.google.gwt.event.shared.HandlerRegistration;
import org.wirez.core.client.shape.view.event.*;

import java.util.HashMap;
import java.util.Map;

public class ViewEventHandlerManager {

    protected final HandlerRegistrationManager registrationManager = new HandlerRegistrationManager();
    protected final Map<ViewEventType, HandlerRegistration[]> registrationMap = new HashMap<>();

    private final Node<?> node;
    private final ViewEventType[] supportedTypes;

    public ViewEventHandlerManager(final Node<?> node, 
                                   final ViewEventType... supportedTypes) {
        this.node = node;
        this.supportedTypes = supportedTypes;
    }

    public boolean supports(final ViewEventType type) {
        
        if ( null != supportedTypes ) {
            
            for ( final ViewEventType type1 : supportedTypes )  {
                
                if ( type.equals( type1 ) ) {
                    return true;
                }
            }
            
        }
        
        return false;
        
    }
    
    @SuppressWarnings("unchecked")
    public void addHandler( final ViewEventType type, 
                         final ViewHandler<? extends ViewEvent> eventHandler ) {

        if ( supports( type ) ) {

            final HandlerRegistration[] registrations = doAddHandler( type, eventHandler );

            addHandlersRegistration( type, registrations );
            
        }
        
    }

    @SuppressWarnings("unchecked")
    public void addHandlersRegistration( final ViewEventType type,
                                      final HandlerRegistration... registrations ) {

        if ( null != registrations && registrations.length > 0 ) {

            registrationMap.put( type, registrations );

            for ( final HandlerRegistration registration : registrations ) {

                registrationManager.register( registration );

            }

        }
        
    }

    @SuppressWarnings("unchecked")
    protected HandlerRegistration[] doAddHandler(final ViewEventType type,
                                                 final ViewHandler<? extends ViewEvent> eventHandler) {

        if ( ViewEventType.MOUSE_CLICK.equals(type) ) {
            return registerClickHandler( (ViewHandler<ViewEvent>) eventHandler );
        }

        if ( ViewEventType.TOUCH.equals(type) ) {
            return registerTouchHandler( (org.wirez.core.client.shape.view.event.TouchHandler) eventHandler );
        }

        if ( ViewEventType.GESTURE.equals(type) ) {
            return registerGestureHandler( (org.wirez.core.client.shape.view.event.GestureHandler) eventHandler );
        }

        return null;

    }

    @SuppressWarnings("unchecked")
    public void removeHandler(final ViewHandler<? extends ViewEvent> eventHandler) {
        
        final ViewEventType type = eventHandler.getType();

        if ( registrationMap.containsKey( type ) ) {

            final HandlerRegistration[] registrations = registrationMap.get( type );

            if ( null != registrations && registrations.length > 0 ) {

                for ( final HandlerRegistration registration : registrations ) {

                    registrationManager.deregister(registration);

                }

            }

        }
        
    }

    @SuppressWarnings("unchecked")
    public void destroy() {
        
        registrationManager.removeHandler();
        
        registrationMap.clear();
        
    }


    protected HandlerRegistration[] registerGestureHandler( final GestureHandler gestureHandler ) {


        HandlerRegistration gestureStartReg = node.addNodeGestureStartHandler(new NodeGestureStartHandler() {
            @Override
            public void onNodeGestureStart( final NodeGestureStartEvent event ) {
                
                final GestureEvent event1 = buildGestureEvent( event );
                
                if ( null != event1 ) {
                    
                    gestureHandler.start( event1 );
                    
                }
                
            }
        } );

        HandlerRegistration gestureChangeReg = node.addNodeGestureChangeHandler(new NodeGestureChangeHandler() {
            @Override
            public void onNodeGestureChange(final NodeGestureChangeEvent event) {

                final GestureEvent event1 = buildGestureEvent( event );

                if ( null != event1 ) {

                    gestureHandler.change( event1 );

                }
                
            }
        } );

        HandlerRegistration gestureEndReg = node.addNodeGestureEndHandler(new NodeGestureEndHandler() {
            @Override
            public void onNodeGestureEnd(final NodeGestureEndEvent event) {

                final GestureEvent event1 = buildGestureEvent( event );

                if ( null != event1 ) {

                    gestureHandler.end( event1 );

                }
                
            }
        } );

        return new HandlerRegistration[] {
                gestureStartReg, gestureChangeReg, gestureEndReg
        };

    }

    protected GestureEventImpl buildGestureEvent(final AbstractNodeGestureEvent event ) {

        return new GestureEventImpl( event.getScale(), event.getRotation() );
        
    }

    protected HandlerRegistration[] registerClickHandler( final ViewHandler<ViewEvent> eventHandler ) {
        
        return new HandlerRegistration[] {
                
                node.addNodeMouseClickHandler(nodeMouseClickEvent -> {
                    
                    final MouseClickEvent event = new MouseClickEvent(nodeMouseClickEvent.getX(), nodeMouseClickEvent.getY());
                    
                    eventHandler.handle( event );
                    
                })
                
        };
        
    }

    protected HandlerRegistration[] registerTouchHandler( final TouchHandler touchHandler ) {


        HandlerRegistration touchStartReg = node.addNodeTouchStartHandler(event -> {

            final TouchEventImpl event1 = buildTouchEvent( event );

            if ( null != event1 ) {

                touchHandler.start( event1 );

            }

        });


        HandlerRegistration touchMoveReg = node.addNodeTouchMoveHandler(event -> {

            final TouchEventImpl event1 = buildTouchEvent( event );

            if ( null != event1 ) {

                touchHandler.move( event1 );

            }

        });

        HandlerRegistration touchEndReg = node.addNodeTouchEndHandler(event -> {

            final TouchEventImpl event1 = buildTouchEvent( event );

            if ( null != event1 ) {

                touchHandler.end( event1 );

            }

        });

        HandlerRegistration touchCancelReg = node.addNodeTouchCancelHandler(event -> {

            final TouchEventImpl event1 = buildTouchEvent( event );

            if ( null != event1 ) {

                touchHandler.cancel( event1 );

            }

        });

        return new HandlerRegistration[] {
                touchStartReg, touchMoveReg, touchEndReg, touchCancelReg
        };

    }

    private TouchEventImpl buildTouchEvent(final AbstractNodeTouchEvent event ) {

        final TouchPoint touchPoint = null != event.getTouches() && !event.getTouches().isEmpty() ?
                (TouchPoint) event.getTouches().get(0) : null;

        if ( null != touchPoint ) {

            final int tx = touchPoint.getX();
            final int ty = touchPoint.getY();


            return new TouchEventImpl( event.getX(), event.getY(), tx, ty );
        }

        return null;
    }

}
