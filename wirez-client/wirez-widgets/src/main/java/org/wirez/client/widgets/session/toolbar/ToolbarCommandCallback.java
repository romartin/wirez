package org.wirez.client.widgets.session.toolbar;

import org.wirez.core.client.service.ClientRuntimeError;

public interface ToolbarCommandCallback<T> {

    void onCommandExecuted( T result);

    void onError(ClientRuntimeError error);

}
