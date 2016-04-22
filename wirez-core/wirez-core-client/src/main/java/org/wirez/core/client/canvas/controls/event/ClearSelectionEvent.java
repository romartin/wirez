package org.wirez.core.client.canvas.controls.event;

import org.jboss.errai.common.client.api.annotations.NonPortable;
import org.wirez.core.client.canvas.AbstractCanvas;

@NonPortable
public final class ClearSelectionEvent extends AbstractSelectionControlEvent<AbstractCanvas> {
    
    public ClearSelectionEvent(final AbstractCanvas canvas) {
        super( canvas );
    }
    
}
