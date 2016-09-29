package org.kie.workbench.common.stunner.core.client.command.impl;

import org.kie.workbench.common.stunner.core.client.canvas.AbstractCanvasHandler;
import org.kie.workbench.common.stunner.core.client.command.AbstractCanvasGraphCommand;
import org.kie.workbench.common.stunner.core.client.command.CanvasViolation;
import org.kie.workbench.common.stunner.core.client.shape.MutationContext;
import org.kie.workbench.common.stunner.core.client.shape.factory.ShapeFactory;
import org.kie.workbench.common.stunner.core.command.CommandResult;
import org.kie.workbench.common.stunner.core.graph.Element;

public abstract class AddCanvasElementCommand<E extends Element> extends AbstractCanvasGraphCommand {
    
    protected E candidate;
    protected ShapeFactory factory;

    public AddCanvasElementCommand(final E candidate, final ShapeFactory factory) {
        this.candidate = candidate;
        this.factory = factory;
    }

    @Override
    public CommandResult<CanvasViolation> doExecute(final AbstractCanvasHandler context) {
        doRegister(context);
        doMutate(context);
        return buildResult();
    }
    
    protected void doRegister(final AbstractCanvasHandler context) {
        context.register( factory, candidate );
    }

    protected void doMutate(final AbstractCanvasHandler context) {
        context.applyElementMutation( candidate, MutationContext.STATIC );
    }
    
}
