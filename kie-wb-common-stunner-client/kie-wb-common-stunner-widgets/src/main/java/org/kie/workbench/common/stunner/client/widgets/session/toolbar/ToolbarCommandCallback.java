package org.kie.workbench.common.stunner.client.widgets.session.toolbar;

import org.kie.workbench.common.stunner.core.client.service.ClientRuntimeError;

public interface ToolbarCommandCallback<T> {

    void onCommandExecuted( T result);

    void onError(ClientRuntimeError error);

}
