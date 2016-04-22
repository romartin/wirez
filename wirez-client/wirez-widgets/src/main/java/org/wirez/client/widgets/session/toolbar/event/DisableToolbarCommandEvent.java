package org.wirez.client.widgets.session.toolbar.event;

import org.jboss.errai.common.client.api.annotations.NonPortable;
import org.wirez.client.widgets.session.toolbar.ToolbarCommand;

@NonPortable
public final class DisableToolbarCommandEvent extends AbstractToolbarCommandEvent {
    
    public DisableToolbarCommandEvent(final ToolbarCommand<?> command) {
        super( command );
    }
    
}
