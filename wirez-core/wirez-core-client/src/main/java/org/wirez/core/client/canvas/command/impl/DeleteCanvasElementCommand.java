package org.wirez.core.client.canvas.command.impl;

import org.wirez.core.api.command.CommandResult;
import org.wirez.core.api.graph.Element;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.content.view.View;
import org.wirez.core.client.canvas.AbstractCanvasHandler;
import org.wirez.core.client.canvas.command.AbstractCanvasGraphCommand;
import org.wirez.core.client.canvas.command.CanvasViolation;
import org.wirez.core.client.factory.ShapeFactory;

public abstract class DeleteCanvasElementCommand<E extends Element> extends AbstractCanvasGraphCommand {

    protected E candidate;
    protected Node parent;
    protected ShapeFactory factory;

    public DeleteCanvasElementCommand(final E candidate) {
        this.candidate = candidate;
        this.parent = getParent();
    }

    @Override
    public CommandResult<CanvasViolation> execute(final AbstractCanvasHandler context) {
        this.factory = getShapeFactory(context);
        doDeregister(context);
        return buildResult();
    }
    
    protected void doDeregister(final AbstractCanvasHandler context) {
        context.deregister(candidate);
    }
    
    protected abstract Node getParent();
    
    protected ShapeFactory getShapeFactory(AbstractCanvasHandler context) {
        return context.getShapeManager().getFactory( ( (Element<View<?>>) candidate ).getContent().getDefinition() );
    }

}
