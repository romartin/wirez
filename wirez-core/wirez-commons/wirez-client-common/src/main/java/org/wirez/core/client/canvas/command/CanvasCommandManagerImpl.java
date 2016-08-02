package org.wirez.core.client.canvas.command;

import org.wirez.core.client.canvas.AbstractCanvasHandler;
import org.wirez.core.client.canvas.event.command.CanvasCommandAllowedEvent;
import org.wirez.core.client.canvas.event.command.CanvasCommandExecutedEvent;
import org.wirez.core.client.canvas.event.command.CanvasUndoCommandExecutedEvent;
import org.wirez.core.command.Command;
import org.wirez.core.command.CommandResult;
import org.wirez.core.graph.command.GraphCommandExecutionContext;
import org.wirez.core.graph.command.GraphCommandExecutionContextImpl;

import javax.enterprise.context.Dependent;
import javax.enterprise.event.Event;
import javax.inject.Inject;

@Dependent
public class CanvasCommandManagerImpl extends AbstractCanvasCommandManager<AbstractCanvasHandler> {

    Event<CanvasCommandAllowedEvent> isCanvasCommandAllowedEvent;
    Event<CanvasCommandExecutedEvent> canvasCommandExecutedEvent;
    Event<CanvasUndoCommandExecutedEvent> canvasUndoCommandExecutedEvent;
    
    @Inject
    public CanvasCommandManagerImpl(final Event<CanvasCommandAllowedEvent> isCanvasCommandAllowedEvent, 
                                    final Event<CanvasCommandExecutedEvent> canvasCommandExecutedEvent,
                                    final Event<CanvasUndoCommandExecutedEvent> canvasUndoCommandExecutedEvent) {
        this.isCanvasCommandAllowedEvent = isCanvasCommandAllowedEvent;
        this.canvasCommandExecutedEvent = canvasCommandExecutedEvent;
        this.canvasUndoCommandExecutedEvent = canvasUndoCommandExecutedEvent;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void afterCommandAllow(final AbstractCanvasHandler context,
                                        final Command<AbstractCanvasHandler, CanvasViolation> command,
                                        final CommandResult<CanvasViolation> result) {
        super.afterCommandAllow( context, command, result );

        if ( null != isCanvasCommandAllowedEvent ) {
            isCanvasCommandAllowedEvent.fire( new CanvasCommandAllowedEvent( context, command, result ) );
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void afterCommandExecuted(final AbstractCanvasHandler context,
                                        final Command<AbstractCanvasHandler, CanvasViolation> command,
                                        final CommandResult<CanvasViolation> result) {
        super.afterCommandExecuted( context, command, result );

        if ( null != canvasCommandExecutedEvent ) {
            canvasCommandExecutedEvent.fire( new CanvasCommandExecutedEvent( context, command, result ) );
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void afterUndoCommandExecuted(final AbstractCanvasHandler context,
                                     final Command<AbstractCanvasHandler, CanvasViolation> command,
                                     final CommandResult<CanvasViolation> result) {
        super.afterCommandAllow( context, command, result );

        if ( null != canvasUndoCommandExecutedEvent ) {
            canvasUndoCommandExecutedEvent.fire( new CanvasUndoCommandExecutedEvent( context, command, result ) );
        }
    }

    @Override
    protected GraphCommandExecutionContext getGraphCommandExecutionContext(final AbstractCanvasHandler context) {

        return  new GraphCommandExecutionContextImpl( context.getClientDefinitionManager(),
                context.getClientFactoryServices().getClientFactoryManager(), context.getRuleManager(), context.getGraphUtils());
        
    }
    
}
