package org.wirez.core.validation.canvas;

import org.wirez.core.client.canvas.CanvasHandler;
import org.wirez.core.validation.ValidationViolation;

public interface CanvasValidationViolation extends ValidationViolation<CanvasHandler> {

    ValidationViolation<?> getRootViolation();

}
