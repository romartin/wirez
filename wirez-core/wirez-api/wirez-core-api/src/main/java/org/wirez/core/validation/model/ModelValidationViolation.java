package org.wirez.core.validation.model;

import org.wirez.core.validation.ValidationViolation;

import javax.validation.ConstraintViolation;

public interface ModelValidationViolation extends ValidationViolation<Object> {

    ConstraintViolation<Object> getConstraintViolation();

}
