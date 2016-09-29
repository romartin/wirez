package org.kie.workbench.common.stunner.core.validation.model;

import org.kie.workbench.common.stunner.core.validation.model.ModelValidator;
import org.kie.workbench.common.stunner.core.validation.model.ModelValidatorCallback;

import javax.enterprise.context.ApplicationScoped;

// TODO

@ApplicationScoped
public class ModelValidatorImpl implements ModelValidator {

    @Override
    public void validate( final Object item,
                          final ModelValidatorCallback callback ) {

        callback.onSuccess();

    }

}
