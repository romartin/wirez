package org.wirez.core.api.service;

import org.jboss.errai.common.client.api.annotations.MapsTo;
import org.jboss.errai.common.client.api.annotations.Portable;

@Portable
public class ServiceResponseImpl implements ServiceResponse {

    final ResponseStatus responseStatus;

    public ServiceResponseImpl(@MapsTo("responseStatus") ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    @Override
    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }
}
