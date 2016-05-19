package org.wirez.client.widgets.session.toolbar.command;

import org.uberfire.ext.widgets.common.client.common.popups.YesNoCancelPopup;
import org.uberfire.mvp.Command;
import org.wirez.client.widgets.session.toolbar.ToolbarCommand;
import org.wirez.client.widgets.session.toolbar.event.DisableToolbarCommandEvent;
import org.wirez.client.widgets.session.toolbar.event.EnableToolbarCommandEvent;
import org.wirez.core.util.UUID;
import org.wirez.core.client.session.CanvasSession;

import javax.enterprise.event.Event;

public abstract class AbstractToolbarCommand<S extends CanvasSession> implements ToolbarCommand<S> {
    
    Event<EnableToolbarCommandEvent> enableToolbarCommandEvent;
    Event<DisableToolbarCommandEvent> disableToolbarCommandEvent;
    String uuid;
    
    protected S session;
    
    public AbstractToolbarCommand(final Event<EnableToolbarCommandEvent> enableToolbarCommandEvent, 
                                  final Event<DisableToolbarCommandEvent> disableToolbarCommandEvent) {
        this.enableToolbarCommandEvent = enableToolbarCommandEvent;
        this.disableToolbarCommandEvent = disableToolbarCommandEvent;
        this.uuid = UUID.uuid();
    }
    
    @Override
    public void initialize(final S session) {
        this.session = session;
    }

    public void afterDraw() {

    }

    @Override
    public void execute() {
        this.execute( null );
    }

    protected void executeWithConfirm( final Command command ) {

        final Command yesCommand = () -> {
            command.execute();
        };

        final Command noCommand = () -> {
        };

        final YesNoCancelPopup popup = YesNoCancelPopup.newYesNoCancelPopup( "Are you sure?",
                null, yesCommand, noCommand, noCommand );

        popup.show();

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

    @Override
    public void destroy() {
        doDestroy();
        this.session = null;
        this.uuid = null;
    }

    protected void doDestroy() {
    }

    protected void enable() {
        enableToolbarCommandEvent.fire( new EnableToolbarCommandEvent( this ) );
    }
    
    protected void disable() {
        disableToolbarCommandEvent.fire( new DisableToolbarCommandEvent( this ) );
    }
    
}
