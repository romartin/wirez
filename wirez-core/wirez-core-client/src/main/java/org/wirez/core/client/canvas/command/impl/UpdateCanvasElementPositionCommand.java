package org.wirez.core.client.canvas.command.impl;

import org.wirez.core.api.command.Command;
import org.wirez.core.api.command.CommandResult;
import org.wirez.core.api.graph.Element;
import org.wirez.core.api.graph.command.factory.GraphCommandFactory;
import org.wirez.core.api.rule.RuleManager;
import org.wirez.core.api.rule.RuleViolation;
import org.wirez.core.client.canvas.command.HasGraphCommand;
import org.wirez.core.client.canvas.command.factory.CanvasCommandFactory;
import org.wirez.core.client.canvas.command.CanvasCommandViolation;
import org.wirez.core.client.canvas.impl.WiresCanvasHandler;

public class UpdateCanvasElementPositionCommand extends AbstractCanvasCommand implements HasGraphCommand<WiresCanvasHandler, GraphCommandFactory> {

    protected Element element;

    Double x;
    Double y;
    
    public UpdateCanvasElementPositionCommand(final CanvasCommandFactory canvasCommandFactory, 
                                              final Element element,
                                              final Double x,
                                              final Double y) {
        super(canvasCommandFactory);
        this.element = element;
        this.x = x;
        this.y = y;
    }

    @Override
    public CommandResult<CanvasCommandViolation> execute(final WiresCanvasHandler context) {
        context.updateElementPosition(element);
        return buildResult();
    }

    @Override
    public CommandResult<CanvasCommandViolation> undo(final WiresCanvasHandler context) {
        // TODO
        return null;
    }

    @Override
    public Command<RuleManager, RuleViolation> getGraphCommand(WiresCanvasHandler canvasHandler, GraphCommandFactory factory) {
        return factory.updateElementPositionCommand( element, x, y );
    }
}
