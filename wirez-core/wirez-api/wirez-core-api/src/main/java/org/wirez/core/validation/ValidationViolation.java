package org.wirez.core.validation;

public interface ValidationViolation<E> {

    E getEntity();

    String getMessage();

}
