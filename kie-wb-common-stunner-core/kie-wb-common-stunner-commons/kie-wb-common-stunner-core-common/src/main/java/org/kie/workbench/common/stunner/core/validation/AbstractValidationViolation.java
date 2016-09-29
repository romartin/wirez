package org.kie.workbench.common.stunner.core.validation;

import org.kie.workbench.common.stunner.core.validation.ValidationViolation;

public abstract class AbstractValidationViolation<E> implements ValidationViolation<E> {

    protected final E entity;

    protected AbstractValidationViolation( final E entity ) {
        this.entity = entity;
    }


    @Override
    public E getEntity() {
        return entity;
    }

}
