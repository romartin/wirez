package org.kie.workbench.common.stunner.core.client.command.impl;

import org.kie.workbench.common.stunner.core.client.canvas.AbstractCanvasHandler;
import org.kie.workbench.common.stunner.core.client.command.AbstractCanvasGraphCommand;
import org.kie.workbench.common.stunner.core.client.command.CanvasViolation;
import org.kie.workbench.common.stunner.core.client.shape.factory.ShapeFactory;
import org.kie.workbench.common.stunner.core.client.util.ShapeUtils;
import org.kie.workbench.common.stunner.core.command.CommandResult;
import org.kie.workbench.common.stunner.core.graph.Element;
import org.kie.workbench.common.stunner.core.graph.Node;

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
    public CommandResult<CanvasViolation> doExecute(final AbstractCanvasHandler context) {
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
