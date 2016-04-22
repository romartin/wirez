package org.wirez.client.widgets.session.toolbar.command;

import org.wirez.client.widgets.session.toolbar.ToolbarCommand;
import org.wirez.client.widgets.session.toolbar.event.DisableToolbarCommandEvent;
import org.wirez.client.widgets.session.toolbar.event.EnableToolbarCommandEvent;
import org.wirez.core.api.util.UUID;
import org.wirez.core.client.session.CanvasSession;

import javax.enterprise.event.Event;

public abstract class AbstractToolbarCommand<S extends CanvasSession> implements ToolbarCommand<S> {
    
    Event<EnableToolbarCommandEvent> enableToolbarCommandEvent;
    Event<DisableToolbarCommandEvent> disableToolbarCommandEvent;
    String uuid;
    
    public AbstractToolbarCommand(final Event<EnableToolbarCommandEvent> enableToolbarCommandEvent, 
                                  final Event<DisableToolbarCommandEvent> disableToolbarCommandEvent) {
        this.enableToolbarCommandEvent = enableToolbarCommandEvent;
        this.disableToolbarCommandEvent = disableToolbarCommandEvent;
        this.uuid = UUID.uuid();
    }
    
    public void afterDraw() {
        
    }

    @Override
    public void execute(final S session) {
        this.execute( session, null );
    }

    @Override
    public boolean equals( final Object o ) {
        if ( this == o ) {
            return true;
        }
        if ( !( o instanceof AbstractToolbarCommand) ) {
            return false;
        }

        AbstractToolbarCommand that = (AbstractToolbarCommand) o;

        return uuid.equals(that.uuid);

    }

    public String getUuid() {
        return uuid;
    }
    
    protected void enable() {
        enableToolbarCommandEvent.fire( new EnableToolbarCommandEvent( this ) );
    }
    
    protected void disable() {
        disableToolbarCommandEvent.fire( new DisableToolbarCommandEvent( this ) );
    }
    
}
