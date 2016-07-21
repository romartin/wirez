package org.wirez.core.validation;

public interface ValidatorCallback<V extends ValidationViolation> {

    void onSuccess();

    void onFail( Iterable<V> violations );

}
