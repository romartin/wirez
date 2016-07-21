package org.wirez.core.validation.model;

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
