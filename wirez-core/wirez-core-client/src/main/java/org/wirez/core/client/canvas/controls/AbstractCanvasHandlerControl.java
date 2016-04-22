package org.wirez.core.client.canvas.controls;

import org.wirez.core.client.canvas.AbstractCanvasHandler;

public abstract class AbstractCanvasHandlerControl implements CanvasControl<AbstractCanvasHandler> {

    protected AbstractCanvasHandler canvasHandler;

    @Override
    public void enable(final AbstractCanvasHandler canvasHandler) {
        this.canvasHandler = canvasHandler;
    }

    @Override
    public void disable() {
        this.canvasHandler = null;
    }
    
    protected boolean isEnabled() {
        return canvasHandler != null;
    }
    
}
