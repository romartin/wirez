package org.kie.workbench.common.stunner.client.widgets.session.toolbar.command;

import org.kie.workbench.common.stunner.client.widgets.session.toolbar.Toolbar;
import org.kie.workbench.common.stunner.client.widgets.session.toolbar.ToolbarCommand;
import org.uberfire.ext.widgets.common.client.common.popups.YesNoCancelPopup;
import org.uberfire.mvp.Command;
import org.kie.workbench.common.stunner.core.client.session.CanvasSession;
import org.kie.workbench.common.stunner.core.util.UUID;

public abstract class AbstractToolbarCommand<S extends CanvasSession> implements ToolbarCommand<S> {
    
    String uuid;
    
    protected Toolbar<S> toolbar;
    protected S session;

    public AbstractToolbarCommand() {
        this.uuid = UUID.uuid();
    }

    protected abstract boolean getState();

    @Override
    public ToolbarCommand<S> initialize( final Toolbar<S> toolbar,
                                         final S session ) {
        this.toolbar = toolbar;
        this.session = session;
        checkState();
        return this;
    }

    protected void checkState() {

        if ( getState() ) {

            enable();

        } else {

            disable();

        }

    }

    public void afterDraw() {
        checkState();
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
        toolbar.enable( this );
    }
    
    protected void disable() {
        toolbar.disable( this );
    }
    
}
