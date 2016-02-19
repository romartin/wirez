package org.wirez.core.client.canvas.command.impl;

import org.wirez.core.api.command.CommandResult;
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Element;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.content.ViewContent;
import org.wirez.core.client.canvas.command.factory.CanvasCommandFactory;
import org.wirez.core.client.canvas.command.CanvasCommandViolation;
import org.wirez.core.client.canvas.impl.WiresCanvasHandler;
import org.wirez.core.client.factory.ShapeFactory;

public abstract class DeleteCanvasElementCommand<E extends Element> extends AbstractCanvasCommand {

    protected E candidate;
    protected Node parent;
    protected ShapeFactory factory;

    public DeleteCanvasElementCommand(final CanvasCommandFactory canvasCommandFactory, final E candidate) {
        super(canvasCommandFactory);
        this.candidate = candidate;
        this.parent = getParent();
    }

    @Override
    public CommandResult<CanvasCommandViolation> execute(final WiresCanvasHandler context) {
        this.factory = getShapeFactory(context);
        context.deregister(candidate);
        return buildResult();
    }
    
    protected abstract Node getParent();
    
    protected ShapeFactory getShapeFactory(WiresCanvasHandler context) {
        return context.getShapeManager().getFactory( ( (Element<ViewContent<?>>) candidate ).getContent().getDefinition() );
    }

}
