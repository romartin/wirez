package org.wirez.client.views;

import com.ait.lienzo.client.core.shape.AbstractDirectionalMultiPointShape;
import com.ait.lienzo.client.core.shape.Decorator;
import com.ait.lienzo.client.core.shape.wires.WiresManager;
import com.google.gwt.event.shared.HandlerRegistration;
import org.wirez.core.client.view.event.ViewEvent;
import org.wirez.core.client.view.event.ViewEventType;
import org.wirez.core.client.view.event.ViewHandler;

public class WiresConnectorView extends AbstractWiresConnectorView<WiresConnectorView> {

    
    public WiresConnectorView(final AbstractDirectionalMultiPointShape<?> line, 
                              final Decorator<?> head, 
                              final Decorator<?> tail, 
                              final WiresManager manager) {
        super(line, head, tail, manager);
    }

    @Override
    protected HandlerRegistration doAddHandler(final ViewEventType type, final ViewHandler<ViewEvent> eventHandler) {
        if ( ViewEventType.MOUSE_CLICK.equals( type ) ) {
            return registerClickHandler(getDecoratableLine(), eventHandler);
        }
        
        return null;
    }

    @Override
    public boolean supports(final ViewEventType type) {
        return ViewEventType.MOUSE_CLICK.equals( type );
    }
}
