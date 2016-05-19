package org.wirez.core.client.canvas.command;

import org.wirez.core.client.canvas.CanvasHandler;
import org.wirez.core.command.Command;
import org.wirez.core.command.CommandResult;
import org.wirez.core.command.CommandResultBuilder;
import org.wirez.core.command.CommandUtils;
import org.wirez.core.command.stack.AbstractStackCommandManager;
import org.wirez.core.graph.command.GraphCommandExecutionContext;
import org.wirez.core.rule.RuleViolation;

import javax.inject.Inject;

public abstract class AbstractCanvasCommandManager<H extends CanvasHandler> extends AbstractStackCommandManager<H, CanvasViolation>
        implements CanvasCommandManager<H> {

    @Inject
    public AbstractCanvasCommandManager() {
    }

    protected abstract GraphCommandExecutionContext getGraphCommandExecutionContext(H context);

    @Override
    @SuppressWarnings("unchecked")
    protected CommandResult<CanvasViolation> doAllow(final H context,
                                                     final Command<H, CanvasViolation> command) {

        CommandResult<CanvasViolation> result = null;
        
        if (command instanceof HasGraphCommand) {

            final Command<GraphCommandExecutionContext, RuleViolation> graphCommand = 
                    ((HasGraphCommand<H>) command).getGraphCommand(context);

            final GraphCommandExecutionContext graphContext = getGraphCommandExecutionContext(context);
            final CommandResult<RuleViolation> graphResult = doGraphCommandAllow(graphContext, graphCommand);

            result = new CanvasCommandResultBuilder(graphResult).build();

        }

        if ( null == result ) {
            
            result = super.doAllow(context, command);
            
        }

        afterCommandAllow( context, command, result );

        return result;

    }

    protected CommandResult<RuleViolation> doGraphCommandAllow(final GraphCommandExecutionContext graphContext,
                                                               final Command<GraphCommandExecutionContext, RuleViolation> graphCommand) {
        return graphCommand.allow(graphContext);
    }

    protected void afterCommandAllow(final H context,
                                     final Command<H, CanvasViolation> command,
                                     final CommandResult<CanvasViolation> result) {
    }


    @Override
    @SuppressWarnings("unchecked")
    protected CommandResult<CanvasViolation> doExecute(final H context,
                                                       final Command<H, CanvasViolation> command) {

        CommandResult<CanvasViolation> result = null;
        
        if (command instanceof HasGraphCommand) {

            // Obtain the command to execute for the graph.
            final Command<GraphCommandExecutionContext, RuleViolation> graphCommand = 
                    ((HasGraphCommand<H>) command).getGraphCommand(context);

            GraphCommandExecutionContext graphContext = getGraphCommandExecutionContext(context);
            final CommandResult<RuleViolation> graphResult = doGraphCommandExecute(graphContext, graphCommand);

            // If there is an error, do not execute operations on the canvas.
            if (CommandUtils.isError(graphResult)) {
                result = new CanvasCommandResultBuilder(graphResult).build();
            }

        }

        if ( null == result ) {

            result = super.doExecute(context, command);
            
        }

        afterCommandExecuted( context, command, result );
        
        return result;
    }

    protected CommandResult<RuleViolation> doGraphCommandExecute(final GraphCommandExecutionContext graphContext,
                                                                 final Command<GraphCommandExecutionContext, RuleViolation> graphCommand) {
        return graphCommand.execute(graphContext);
    }

    protected void afterCommandExecuted(final H context,
                                        final Command<H, CanvasViolation> command,
                                        final CommandResult<CanvasViolation> result) {
    }

    @Override
    public CommandResult<CanvasViolation> undo(final H context) {
        final CommandResult<CanvasViolation> result = super.undo(context);
        context.getCanvas().draw();
        return result;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected CommandResult<CanvasViolation> doUndo(final H context, 
                                                    final Command<H, CanvasViolation> command) {
        
        CommandResult<CanvasViolation> result = null;
        
        if (null != command && (command instanceof HasGraphCommand)) {

            final Command<GraphCommandExecutionContext, RuleViolation> graphCommand = 
                    ((HasGraphCommand<H>) command).getGraphCommand(context);

            final GraphCommandExecutionContext graphContext = getGraphCommandExecutionContext(context);
            final CommandResult<RuleViolation> graphCommandResult = doGraphCommandUndo(graphContext, graphCommand);

            // If there is an error, do not execute operations on the canvas.
            if (CommandUtils.isError(graphCommandResult)) {
                result = new CanvasCommandResultBuilder(graphCommandResult).build();
            }

        }

        if ( null == result ) {

            result = super.doUndo(context, command);

        }

        afterUndoCommandExecuted( context, command, result );

        return result;
    }

    protected CommandResult<RuleViolation> doGraphCommandUndo(final GraphCommandExecutionContext graphContext,
                                                              final Command<GraphCommandExecutionContext, RuleViolation> graphCommand) {
        return graphCommand.undo(graphContext);
    }

    protected void afterUndoCommandExecuted(final H context,
                                            final Command<H, CanvasViolation> command,
                                            final CommandResult<CanvasViolation> result) {
    }

    @Override
    protected CommandResultBuilder<CanvasViolation> buildCommandResultBuilder() {
        return new CanvasCommandResultBuilder();
    }

}
