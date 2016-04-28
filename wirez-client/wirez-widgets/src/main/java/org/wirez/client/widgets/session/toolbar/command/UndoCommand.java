package org.wirez.client.widgets.session.toolbar.command;

import org.gwtbootstrap3.client.ui.constants.IconType;
import org.wirez.client.widgets.session.toolbar.ToolbarCommandCallback;
import org.wirez.client.widgets.session.toolbar.event.DisableToolbarCommandEvent;
import org.wirez.client.widgets.session.toolbar.event.EnableToolbarCommandEvent;
import org.wirez.core.api.command.CommandResult;
import org.wirez.core.api.command.stack.StackCommandManager;
import org.wirez.core.api.event.local.CommandExecutedEvent;
import org.wirez.core.api.event.local.CommandUndoExecutedEvent;
import org.wirez.core.client.canvas.command.CanvasViolation;
import org.wirez.core.client.session.impl.DefaultCanvasFullSession;

import javax.enterprise.context.Dependent;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import static org.uberfire.commons.validation.PortablePreconditions.checkNotNull;

@Dependent
public class UndoCommand extends AbstractToolbarCommand<DefaultCanvasFullSession> {

    @Inject
    public UndoCommand(final Event<EnableToolbarCommandEvent> enableToolbarCommandEvent,
                       final Event<DisableToolbarCommandEvent> disableToolbarCommandEvent) {
        super( enableToolbarCommandEvent, disableToolbarCommandEvent );
    }

    @Override
    public IconType getIcon() {
        return IconType.UNDO;
    }

    @Override
    public String getCaption() {
        return null;
    }

    @Override
    public String getTooltip() {
        return "Undo the latest successfully executed command";
    }

    @Override
    public void initialize(final DefaultCanvasFullSession session) {
        super.initialize(session);
        checkState();
    }

    @Override
    public <T> void execute(final ToolbarCommandCallback<T> callback) {

        final CommandResult<CanvasViolation> result = 
                session.getCanvasCommandManager().undo( session.getCanvasHandler() );

        if (null != callback) {
            
            callback.onSuccess((T) result);
            
        }
        
    }
    
    void onCommandExecuted(@Observes CommandExecutedEvent commandExecutedEvent) {
        checkNotNull("commandExecutedEvent", commandExecutedEvent);
        checkState();
    }

    void onCommandUndoExecuted(@Observes CommandUndoExecutedEvent commandUndoExecutedEvent) {
        checkNotNull("commandUndoExecutedEvent", commandUndoExecutedEvent);
        checkState();
    }
    
    private void checkState() {
        
        if ( null != session ) {
            
            final StackCommandManager<?, ?> canvasCommManager = (StackCommandManager<?, ?>) session.getCanvasCommandManager();
            final boolean isHistoryEmpty = canvasCommManager.getHistorySize() == 0;

            if ( isHistoryEmpty ) {
                disable();
            } else {
                enable();
            }
            
        }
        
    }
    
}
