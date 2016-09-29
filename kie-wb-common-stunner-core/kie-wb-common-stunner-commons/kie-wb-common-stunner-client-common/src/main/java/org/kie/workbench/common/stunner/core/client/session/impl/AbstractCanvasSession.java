package org.kie.workbench.common.stunner.core.client.session.impl;

import org.kie.workbench.common.stunner.core.client.canvas.AbstractCanvas;
import org.kie.workbench.common.stunner.core.client.canvas.AbstractCanvasHandler;
import org.kie.workbench.common.stunner.core.util.UUID;

public abstract class AbstractCanvasSession implements DefaultCanvasSession {
    
    protected final transient AbstractCanvas canvas;
    protected final transient AbstractCanvasHandler canvasHandler;
    private final transient String uuid;

    public AbstractCanvasSession(final AbstractCanvas canvas, 
                                 final AbstractCanvasHandler canvasHandler) {
        this.uuid = UUID.uuid();
        this.canvas = canvas;
        this.canvasHandler = canvasHandler;
    }

    protected abstract void doDispose();
    
    @Override
    public AbstractCanvas getCanvas() {
        return canvas;
    }

    @Override
    public AbstractCanvasHandler getCanvasHandler() {
        return canvasHandler;
    }

    @Override
    public void onOpen() {
        
    }

    public void onDispose() {
        doDispose();
        canvasHandler.destroy();
    }

    @Override
    public boolean equals( final Object o ) {
        
        if ( this == o ) {
            return true;
        }
        
        if ( !( o instanceof AbstractCanvasSession) ) {
            return false;
        }

        AbstractCanvasSession that = (AbstractCanvasSession) o;

        return uuid.equals( that.uuid );
    }

    @Override
    public String toString() {
        return "AbstractCanvasSession [uuid=" + uuid + "]"; 
    }
}
