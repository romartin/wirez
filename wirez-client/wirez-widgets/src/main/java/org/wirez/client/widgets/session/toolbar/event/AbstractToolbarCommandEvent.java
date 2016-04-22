package org.wirez.client.widgets.session.toolbar.event;

import org.jboss.errai.common.client.api.annotations.NonPortable;
import org.uberfire.workbench.events.UberFireEvent;
import org.wirez.client.widgets.session.toolbar.ToolbarCommand;

@NonPortable
public abstract class AbstractToolbarCommandEvent implements UberFireEvent {
    
    private final ToolbarCommand<?> command;

    public AbstractToolbarCommandEvent(final ToolbarCommand<?> command) {
        this.command = command;
    }

    public ToolbarCommand<?> getCommand() {
        return command;
    }
    
}
