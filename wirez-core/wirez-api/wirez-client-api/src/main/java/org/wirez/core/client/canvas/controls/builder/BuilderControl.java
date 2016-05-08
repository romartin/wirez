package org.wirez.core.client.canvas.controls.builder;

import org.wirez.core.client.canvas.CanvasHandler;
import org.wirez.core.client.canvas.controls.CanvasControl;

public interface BuilderControl<C extends CanvasHandler, R extends BuildRequest> extends CanvasControl<C> {

    boolean allows(R request);
    
    void build(R request);

}
