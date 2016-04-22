package org.wirez.client.widgets.event;

import org.jboss.errai.common.client.api.annotations.NonPortable;
import org.uberfire.workbench.events.UberFireEvent;
import org.wirez.core.client.canvas.CanvasHandler;
import org.wirez.core.client.canvas.event.AbstractCanvasHandlerEvent;

@NonPortable
public final class WidgetProcessingStartedEvent implements UberFireEvent {

    public WidgetProcessingStartedEvent() {
        
    }
    
}
