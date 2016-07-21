package org.wirez.core.validation.model;

import org.wirez.core.validation.AbstractValidationViolation;

import javax.validation.ConstraintViolation;

public final class ModelValidationViolationImpl
        extends AbstractValidationViolation<Object>
        implements ModelValidationViolation {

    private final ConstraintViolation<Object> violation;

    protected ModelValidationViolationImpl( final Object entity,
                                            final ConstraintViolation<Object> violation ) {
        super( entity );
        this.violation = violation;
    }


    @Override
    public String getMessage() {
        return violation.getMessage();
    }

    @Override
    public ConstraintViolation<Object> getConstraintViolation() {
        return violation;
    }

}
