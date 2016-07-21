package org.wirez.core.validation.event;

public abstract class AbstractValidationEvent<E> {

    private final E entity;

    public AbstractValidationEvent( final E entity ) {
        this.entity = entity;
    }

    public E getEntity() {
        return entity;
    }

}
