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
    protected CommandResult<CanvasViolation> doAllow(final H context,
                                                     final Command<H, CanvasViolation> command) {

        Command<GraphCommandExecutionContext, RuleViolation> graphCommand = null;
        CommandResult<RuleViolation> graphResult = null;
        if (command instanceof HasGraphCommand) {

            graphCommand = ((HasGraphCommand<H>) command).getGraphCommand(context);

            final GraphCommandExecutionContext graphContext = getGraphCommandExecutionContext(context);
            graphResult = doGraphCommandAllow(graphContext, graphCommand);

            if (null != graphCommand && null != graphResult) {
                afterGraphCommandAllow(graphCommand, graphResult);
            }

            return new CanvasCommandResultBuilder(graphResult).build();

        }

        final CommandResult<CanvasViolation> result = super.doAllow(context, command);

        if (null != graphCommand && null != graphResult) {
            afterGraphCommandAllow(graphCommand, graphResult);
        }

        return result;

    }

    protected CommandResult<RuleViolation> doGraphCommandAllow(final GraphCommandExecutionContext graphContext,
                                                               final Command<GraphCommandExecutionContext, RuleViolation> graphCommand) {
        return graphCommand.allow(graphContext);
    }

    protected void afterGraphCommandAllow(final Command<GraphCommandExecutionContext, RuleViolation> graphCommand,
                                          final CommandResult<RuleViolation> result) {
    }


    @Override
    @SuppressWarnings("unchecked")
    protected CommandResult<CanvasViolation> doExecute(final H context,
                                                       final Command<H, CanvasViolation> command) {

        Command<GraphCommandExecutionContext, RuleViolation> graphCommand = null;
        CommandResult<RuleViolation> graphResult = null;
        if (command instanceof HasGraphCommand) {

            // Obtain the command to execute for the graph.
            graphCommand = ((HasGraphCommand<H>) command).getGraphCommand(context);

            GraphCommandExecutionContext graphContext = getGraphCommandExecutionContext(context);
            graphResult = doGraphCommandExecute(graphContext, graphCommand);

            // If there is an error, do not execute operations on the canvas.
            if (CommandUtils.isError(graphResult)) {
                afterGraphCommandExecuted(graphCommand, graphResult);
                return new CanvasCommandResultBuilder(graphResult).build();
            }

        }

        final CommandResult<CanvasViolation> result = super.doExecute(context, command);

        if (null != graphCommand && null != graphResult) {
            afterGraphCommandExecuted(graphCommand, graphResult);
        }

        return result;
    }

    protected CommandResult<RuleViolation> doGraphCommandExecute(final GraphCommandExecutionContext graphContext,
                                                                 final Command<GraphCommandExecutionContext, RuleViolation> graphCommand) {
        return graphCommand.execute(graphContext);
    }

    protected void afterGraphCommandExecuted(final Command<GraphCommandExecutionContext, RuleViolation> graphCommand,
                                             final CommandResult<RuleViolation> result) {
    }

    @Override
    public CommandResult<CanvasViolation> undo(final H context) {
        final CommandResult<CanvasViolation> result = super.undo(context);
        context.getCanvas().draw();
        return result;
    }

    @Override
    protected CommandResult<CanvasViolation> doUndo(final H context, 
                                                    final Command<H, CanvasViolation> command) {

        Command<GraphCommandExecutionContext, RuleViolation> graphCommand = null;
        CommandResult<RuleViolation> graphCommandResult = null;

        if (null != command && (command instanceof HasGraphCommand)) {

            graphCommand = ((HasGraphCommand<H>) command).getGraphCommand(context);

            final GraphCommandExecutionContext graphContext = getGraphCommandExecutionContext(context);
            graphCommandResult = doGraphCommandUndo(graphContext, graphCommand);

            // If there is an error, do not execute operations on the canvas.
            if (CommandUtils.isError(graphCommandResult)) {
                // Return the errors.
                afterGraphUndoCommandExecuted(graphCommand, graphCommandResult);
                return new CanvasCommandResultBuilder(graphCommandResult).build();
            }

        }


        final CommandResult<CanvasViolation> result = super.doUndo(context, command);

        if (null != graphCommand && null != graphCommandResult) {
            afterGraphUndoCommandExecuted(graphCommand, graphCommandResult);
        }

        return result;
    }

    protected CommandResult<RuleViolation> doGraphCommandUndo(final GraphCommandExecutionContext graphContext,
                                                              final Command<GraphCommandExecutionContext, RuleViolation> graphCommand) {
        return graphCommand.undo(graphContext);
    }

    protected void afterGraphUndoCommandExecuted(final Command<GraphCommandExecutionContext, RuleViolation> graphCommand,
                                                 final CommandResult<RuleViolation> result) {
    }

    @Override
    protected CommandResultBuilder<CanvasViolation> buildCommandResultBuilder() {
        return new CanvasCommandResultBuilder();
    }

}
