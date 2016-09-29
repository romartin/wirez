package org.kie.workbench.common.stunner.core.client.service;

public interface ServiceCallback<T> {

    void onSuccess(T item);

    void onError(ClientRuntimeError error);
    
}
