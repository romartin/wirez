package org.wirez.core.client.canvas.command;

import org.wirez.core.api.command.Command;
import org.wirez.core.api.command.CommandResult;
import org.wirez.core.api.event.local.CommandExecutedEvent;
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
    
    private GraphCommandExecutionContext instance;

    @Inject
    public CanvasCommandManagerImpl(final Event<IsCommandAllowedEvent> isCommandAllowedEvent, 
                                    final Event<CommandExecutedEvent> commandExecutedEvent) {
        this.isCommandAllowedEvent = isCommandAllowedEvent;
        this.commandExecutedEvent = commandExecutedEvent;
    }

    @Override
    protected CommandResult<RuleViolation> doGraphCommandAllow(final GraphCommandExecutionContext graphContext, 
                                                               final Command<GraphCommandExecutionContext, RuleViolation> graphCommand) {
        final CommandResult<RuleViolation> result = super.doGraphCommandAllow( graphContext, graphCommand );
        isCommandAllowedEvent.fire( new IsCommandAllowedEvent( graphCommand, result ));
        return result;
    }

    @Override
    protected CommandResult<RuleViolation> doGraphCommandExecute(final GraphCommandExecutionContext graphContext,
                                                                 final Command<GraphCommandExecutionContext, RuleViolation> graphCommand) {
        final CommandResult<RuleViolation> result = super.doGraphCommandExecute( graphContext, graphCommand );
        commandExecutedEvent.fire( new CommandExecutedEvent( graphCommand, result) );
        return result;
    }

    @Override
    protected CommandResult<RuleViolation> doGraphCommandUndo(final GraphCommandExecutionContext graphContext,
                                                              final Command<GraphCommandExecutionContext, RuleViolation> graphCommand) {
        final CommandResult<RuleViolation> result = super.doGraphCommandUndo( graphContext, graphCommand );
        commandExecutedEvent.fire( new CommandExecutedEvent( graphCommand, result) );
        return result;
    }

    @Override
    protected GraphCommandExecutionContext getGraphCommandExecutionContext(final AbstractCanvasHandler context) {
        
        if ( null == instance ) {
            
            instance = new GraphCommandExecutionContextImpl( context.getClientDefinitionManager(),
                    context.getClientFactoryServices(), context.getRuleManager(), context.getGraphCommandFactory(),
                    context.getGraphUtils());
            
        }
        
        return instance;
        
    }
    
}
