package org.kie.workbench.common.stunner.core.validation;

public interface ValidationViolation<E> {

    E getEntity();

    String getMessage();

}
