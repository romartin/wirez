package org.wirez.core.client.canvas.command.impl;

import org.wirez.core.api.command.CommandResult;
import org.wirez.core.api.graph.Element;
import org.wirez.core.client.canvas.command.CanvasCommandViolation;
import org.wirez.core.client.canvas.command.factory.CanvasCommandFactory;
import org.wirez.core.client.canvas.wires.WiresCanvasHandler;

public class UpdateCanvasElementPropertiesCommand extends AbstractCanvasCommand {

    protected Element element;
    
    public UpdateCanvasElementPropertiesCommand(final CanvasCommandFactory canvasCommandFactory,
                                                final Element element) {
        super(canvasCommandFactory);
        this.element = element;
    }

    @Override
    public CommandResult<CanvasCommandViolation> execute(final WiresCanvasHandler context) {
        context.updateElementProperties(element);
        return buildResult();
    }

    @Override
    public CommandResult<CanvasCommandViolation> undo(final WiresCanvasHandler context) {
        // TODO
        return null;
    }

}
