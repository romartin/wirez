package org.wirez.core.client.canvas.command;

import org.wirez.core.api.command.Command;
import org.wirez.core.api.command.CommandResult;
import org.wirez.core.api.event.local.CommandExecutedEvent;
import org.wirez.core.api.event.local.CommandUndoExecutedEvent;
import org.wirez.core.api.event.local.IsCommandAllowedEvent;
import org.wirez.core.api.graph.command.GraphCommandExecutionContext;
import org.wirez.core.api.graph.command.GraphCommandExecutionContextImpl;
import org.wirez.core.api.rule.RuleViolation;
import org.wirez.core.client.canvas.AbstractCanvasHandler;

import javax.enterprise.context.Dependent;
import javax.enterprise.event.Event;
import javax.inject.Inject;

@Dependent
public class CanvasCommandManagerImpl extends AbstractCanvasCommandManager<AbstractCanvasHandler> {

    Event<IsCommandAllowedEvent> isCommandAllowedEvent;
    Event<CommandExecutedEvent> commandExecutedEvent;
    Event<CommandUndoExecutedEvent> commandUndoExecutedEvent;
    
    @Inject
    public CanvasCommandManagerImpl(final Event<IsCommandAllowedEvent> isCommandAllowedEvent, 
                                    final Event<CommandExecutedEvent> commandExecutedEvent,
                                    final Event<CommandUndoExecutedEvent> commandUndoExecutedEvent) {
        this.isCommandAllowedEvent = isCommandAllowedEvent;
        this.commandExecutedEvent = commandExecutedEvent;
        this.commandUndoExecutedEvent = commandUndoExecutedEvent;
    }

    @Override
    protected void afterGraphCommandAllow(Command<GraphCommandExecutionContext, RuleViolation> graphCommand, CommandResult<RuleViolation> result) {
        super.afterGraphCommandAllow(graphCommand, result);

        if ( null != isCommandAllowedEvent ) {
            isCommandAllowedEvent.fire( new IsCommandAllowedEvent( graphCommand, result ));
        }
        
    }

    @Override
    protected void afterGraphCommandExecuted( final Command<GraphCommandExecutionContext, RuleViolation> graphCommand, 
                                              final CommandResult<RuleViolation> result) {
        super.afterGraphCommandExecuted(graphCommand, result);

        if ( null != commandExecutedEvent ) {
            commandExecutedEvent.fire( new CommandExecutedEvent( graphCommand, result) );
        }
        
    }

    @Override
    protected void afterGraphUndoCommandExecuted(Command<GraphCommandExecutionContext, RuleViolation> graphCommand, CommandResult<RuleViolation> result) {
        super.afterGraphUndoCommandExecuted(graphCommand, result);

        if ( null != commandUndoExecutedEvent ) {
            commandUndoExecutedEvent.fire( new CommandUndoExecutedEvent( graphCommand, result) );
        }
        
    }

    @Override
    protected GraphCommandExecutionContext getGraphCommandExecutionContext(final AbstractCanvasHandler context) {

        return  new GraphCommandExecutionContextImpl( context.getClientDefinitionManager(),
                context.getClientFactoryServices(), context.getRuleManager(), context.getGraphUtils());
        
    }
    
}
