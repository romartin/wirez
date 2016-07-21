package org.wirez.core.validation;

public interface Validator<E, K extends ValidatorCallback> {

    void validate( E item, K callback );

}
