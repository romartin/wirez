package org.wirez.core.client.canvas.command;

import org.wirez.core.api.command.AbstractCommandResult;
import org.wirez.core.api.rule.RuleViolation;

public class CanvasCommandResult extends AbstractCommandResult<CanvasCommandViolation> {

    @Override
    protected boolean isError(final CanvasCommandViolation violation) {
        return RuleViolation.Type.ERROR.equals(violation.getModelViolation().iterator().next().getViolationType());
    }

    @Override
    protected String getMessage(final CanvasCommandViolation violation) {
        return violation.getMessage();
    }
}
