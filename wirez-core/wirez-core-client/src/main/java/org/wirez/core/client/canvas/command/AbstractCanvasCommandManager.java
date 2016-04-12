package org.wirez.core.client.canvas.command;

import org.wirez.core.api.command.Command;
import org.wirez.core.api.command.CommandResult;
import org.wirez.core.api.command.CommandResultBuilder;
import org.wirez.core.api.command.CommandUtils;
import org.wirez.core.api.command.stack.StackCommandManager;
import org.wirez.core.api.graph.command.GraphCommandExecutionContext;
import org.wirez.core.api.rule.RuleViolation;
import org.wirez.core.client.canvas.CanvasHandler;

import javax.inject.Inject;

public abstract class AbstractCanvasCommandManager<H extends CanvasHandler> extends StackCommandManager<H, CanvasViolation>
        implements CanvasCommandManager<H> {

    @Inject
    public AbstractCanvasCommandManager() {
    }
    
    protected abstract GraphCommandExecutionContext getGraphCommandExecutionContext(H context);
    
    @Override
    @SuppressWarnings("unchecked")
    protected CommandResult<CanvasViolation> doAllow( final H context,
                                                    final Command<H, CanvasViolation> command) {

        if ( command instanceof HasGraphCommand ) {
            
            final Command<GraphCommandExecutionContext, RuleViolation> graphCommand =
                    ((HasGraphCommand<H>) command).getGraphCommand( context );
            
            final GraphCommandExecutionContext graphContext = getGraphCommandExecutionContext( context );
            final CommandResult<RuleViolation> graphResult = doGraphCommandAllow( graphContext, graphCommand );
            
            return new CanvasCommandResultBuilder( graphResult ).build();
            
        }

        return super.doAllow(context, command);
    }
    
    protected CommandResult<RuleViolation> doGraphCommandAllow( final GraphCommandExecutionContext graphContext,
                                                                final Command<GraphCommandExecutionContext, RuleViolation> graphCommand) {
        return graphCommand.allow( graphContext );
    }

    @Override
    @SuppressWarnings("unchecked")
    protected CommandResult<CanvasViolation> doExecute( final H context, 
                                                      final Command<H, CanvasViolation> command) {

        if ( command instanceof HasGraphCommand ) {
            
            final Command<GraphCommandExecutionContext, RuleViolation> graphCommand = 
                    ((HasGraphCommand<H>) command).getGraphCommand( context );

            final GraphCommandExecutionContext graphContext = getGraphCommandExecutionContext( context );
            final CommandResult<RuleViolation> graphResult = doGraphCommandExecute( graphContext, graphCommand );
            
            // If there is an error, do not execute operations on the canvas.
            if ( CommandUtils.isError(graphResult) ) {
                return new CanvasCommandResultBuilder( graphResult ).build();
            }
            
        }
        
        return super.doExecute(context, command);
    }

    protected CommandResult<RuleViolation> doGraphCommandExecute( final GraphCommandExecutionContext graphContext,
                                                                final Command<GraphCommandExecutionContext, RuleViolation> graphCommand) {
        return graphCommand.execute( graphContext );
    }

    @Override
    public CommandResult<CanvasViolation> undo(final H context) {
        final CommandResult<CanvasViolation> result = super.undo(context);
        context.getCanvas().draw();
        return result;
    }

    @Override
    protected CommandResult<CanvasViolation> doUndo(H context, Command<H, CanvasViolation> command) {

        if ( command instanceof HasGraphCommand ) {

            final Command<GraphCommandExecutionContext, RuleViolation> graphCommand =
                    ((HasGraphCommand<H>) command).getGraphCommand( context );

            final GraphCommandExecutionContext graphContext = getGraphCommandExecutionContext( context );
            CommandResult<RuleViolation> graphCommandResult = doGraphCommandUndo( graphContext, graphCommand );

            // If there is an error, do not execute operations on the canvas.
            if ( CommandUtils.isError(graphCommandResult) ) {
                return new CanvasCommandResultBuilder( graphCommandResult ).build();
            }

        }
        
        return super.doUndo(context, command);
    }

    protected CommandResult<RuleViolation> doGraphCommandUndo( final GraphCommandExecutionContext graphContext,
                                                                  final Command<GraphCommandExecutionContext, RuleViolation> graphCommand) {
        return graphCommand.undo( graphContext );
    }

    @Override
    protected CommandResultBuilder<CanvasViolation> buildCommandResultBuilder() {
        return new CanvasCommandResultBuilder();
    }

}
