package org.kie.workbench.common.stunner.core.validation;

public interface Validator<E, K extends ValidatorCallback> {

    void validate( E item, K callback );

}
