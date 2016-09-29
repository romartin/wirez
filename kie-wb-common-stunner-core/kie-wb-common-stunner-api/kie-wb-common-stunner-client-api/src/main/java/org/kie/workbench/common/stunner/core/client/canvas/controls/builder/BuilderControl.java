package org.kie.workbench.common.stunner.core.client.canvas.controls.builder;

import org.kie.workbench.common.stunner.core.client.canvas.CanvasHandler;
import org.kie.workbench.common.stunner.core.client.canvas.controls.CanvasControl;
import org.kie.workbench.common.stunner.core.client.service.ClientRuntimeError;

public interface BuilderControl<C extends CanvasHandler, R extends BuildRequest> extends CanvasControl<C> {

    interface BuildCallback {

        void onSuccess( String uuid );

        void onError( final ClientRuntimeError error );

    }

    boolean allows( R request );

    void build( R request, BuildCallback callback );

}
