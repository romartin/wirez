package org.wirez.core.client.canvas.command.impl;

import org.wirez.core.client.canvas.AbstractCanvasHandler;
import org.wirez.core.client.canvas.command.AbstractCanvasGraphCommand;
import org.wirez.core.client.canvas.command.CanvasViolation;
import org.wirez.core.client.shape.factory.ShapeFactory;
import org.wirez.core.client.util.ShapeUtils;
import org.wirez.core.command.CommandResult;
import org.wirez.core.graph.Element;
import org.wirez.core.graph.Node;

public abstract class DeleteCanvasElementCommand<E extends Element> extends AbstractCanvasGraphCommand {

    protected E candidate;
    protected Node parent;
    protected ShapeFactory factory;

    public DeleteCanvasElementCommand(final E candidate) {
        this.candidate = candidate;
        this.parent = getParent();
    }

    public DeleteCanvasElementCommand(final E candidate, final Node parent) {
        this.candidate = candidate;
        this.parent = parent;
    }

    @Override
    public CommandResult<CanvasViolation> execute(final AbstractCanvasHandler context) {
        this.factory = ShapeUtils.getDefaultShapeFactory( context, candidate );
        doDeregister( context );
        return buildResult();
    }
    
    protected void doDeregister(final AbstractCanvasHandler context) {
        context.deregister( candidate );
    }
    
    protected Node getParent() {
        return null;
    };

}
