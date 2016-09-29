package org.kie.workbench.common.stunner.core.client.validation.canvas;

import org.kie.workbench.common.stunner.core.client.canvas.CanvasHandler;
import org.kie.workbench.common.stunner.core.validation.ValidationViolation;

public interface CanvasValidationViolation extends ValidationViolation<CanvasHandler> {

    ValidationViolation<?> getRootViolation();

}
