package org.wirez.client.views.primitives;

import com.ait.tooling.nativetools.client.event.HandlerRegistrationManager;
import com.google.gwt.event.shared.HandlerRegistration;
import org.wirez.core.client.view.event.ViewEvent;
import org.wirez.core.client.view.event.ViewHandler;
import org.wirez.core.client.view.event.ViewEventType;
import org.wirez.core.client.view.HasEventHandlers;
import org.wirez.core.client.view.ShapeView;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractPrimitiveView<T> implements ShapeView<T>, HasEventHandlers<T> {
    
    protected final HandlerRegistrationManager registrationManager = new HandlerRegistrationManager();
    protected final Map<ViewEventType, HandlerRegistration> registrationMap = new HashMap<>();

    protected abstract HandlerRegistration doAddHandler(final ViewEventType type,
                                                        final ViewHandler<ViewEvent> eventHandler);

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
