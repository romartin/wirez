package org.kie.workbench.common.stunner.core.client.canvas.controls;

import org.kie.workbench.common.stunner.core.client.canvas.AbstractCanvasHandler;

public abstract class AbstractCanvasHandlerControl implements CanvasControl<AbstractCanvasHandler> {

    protected AbstractCanvasHandler canvasHandler;

    protected abstract void doDisable();
    
    @Override
    public void enable(final AbstractCanvasHandler canvasHandler) {
        this.canvasHandler = canvasHandler;
    }

    @Override
    public void disable() {
        doDisable();
        
        this.canvasHandler = null;
    }
    
    protected boolean isEnabled() {
        return canvasHandler != null;
    }
    
}
