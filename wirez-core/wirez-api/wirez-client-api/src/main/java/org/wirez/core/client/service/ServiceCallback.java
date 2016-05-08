package org.wirez.core.client.service;

public interface ServiceCallback<T> {

    void onSuccess(T item);

    void onError(ClientRuntimeError error);
    
}
