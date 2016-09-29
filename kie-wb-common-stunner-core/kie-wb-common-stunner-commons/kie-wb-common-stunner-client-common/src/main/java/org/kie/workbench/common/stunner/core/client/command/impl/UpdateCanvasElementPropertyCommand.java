package org.kie.workbench.common.stunner.core.client.command.impl;

import org.kie.workbench.common.stunner.core.client.canvas.AbstractCanvasHandler;
import org.kie.workbench.common.stunner.core.client.command.AbstractCanvasGraphCommand;
import org.kie.workbench.common.stunner.core.client.command.CanvasViolation;
import org.kie.workbench.common.stunner.core.client.shape.MutationContext;
import org.kie.workbench.common.stunner.core.command.Command;
import org.kie.workbench.common.stunner.core.command.CommandResult;
import org.kie.workbench.common.stunner.core.graph.Element;
import org.kie.workbench.common.stunner.core.graph.command.GraphCommandExecutionContext;
import org.kie.workbench.common.stunner.core.graph.command.impl.UpdateElementPropertyValueCommand;
import org.kie.workbench.common.stunner.core.rule.RuleViolation;

public final class UpdateCanvasElementPropertyCommand extends AbstractCanvasGraphCommand {

    protected Element element;
    protected String propertyId;
    protected Object value;
    
    public UpdateCanvasElementPropertyCommand(final Element element,
                                              final String propertyId,
                                              final Object value) {
        this.element = element;
        this.propertyId = propertyId;
        this.value = value;
    }

    @Override
    public CommandResult<CanvasViolation> doExecute(final AbstractCanvasHandler context) {
        context.updateElementProperties(element, MutationContext.STATIC);
        return buildResult();
    }

    @Override
    public CommandResult<CanvasViolation> doUndo(final AbstractCanvasHandler context) {
        return doExecute( context );
    }

    @Override
    protected Command<GraphCommandExecutionContext, RuleViolation> buildGraphCommand(final AbstractCanvasHandler context) {
        return new UpdateElementPropertyValueCommand(element, propertyId, value);
    }
    
}
