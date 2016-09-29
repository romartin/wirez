package org.kie.workbench.common.stunner.core.validation;

public interface ValidatorCallback<V extends ValidationViolation> {

    void onSuccess();

    void onFail( Iterable<V> violations );

}
