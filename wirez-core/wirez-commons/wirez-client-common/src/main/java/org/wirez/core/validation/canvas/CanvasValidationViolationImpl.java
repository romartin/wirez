package org.wirez.core.validation.canvas;

import org.wirez.core.client.canvas.CanvasHandler;
import org.wirez.core.validation.AbstractValidationViolation;
import org.wirez.core.validation.ValidationViolation;

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
