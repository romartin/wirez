package org.kie.workbench.common.stunner.core.validation.model;

import org.kie.workbench.common.stunner.core.validation.ValidationViolation;

import javax.validation.ConstraintViolation;

public interface ModelValidationViolation extends ValidationViolation<Object> {

    ConstraintViolation<Object> getConstraintViolation();

}
