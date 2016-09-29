package org.kie.workbench.common.stunner.core.client.validation.canvas;

import org.kie.workbench.common.stunner.core.client.canvas.CanvasHandler;
import org.kie.workbench.common.stunner.core.client.validation.canvas.CanvasValidationViolation;
import org.kie.workbench.common.stunner.core.validation.AbstractValidationViolation;
import org.kie.workbench.common.stunner.core.validation.ValidationViolation;

public final class CanvasValidationViolationImpl
        extends AbstractValidationViolation<CanvasHandler>
        implements CanvasValidationViolation {

    private final ValidationViolation<?> root;

    protected CanvasValidationViolationImpl( final CanvasHandler entity,
                                             final ValidationViolation<?> root ) {
        super( entity );
        this.root = root;
    }

    @Override
    public ValidationViolation<?> getRootViolation() {
        return root;
    }

    @Override
    public String getMessage() {
        return root.getMessage();
    }

    @Override
    public String toString() {
        return getMessage();
    }

}
