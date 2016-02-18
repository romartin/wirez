package org.wirez.core.client.canvas.listener;

import org.wirez.core.api.graph.Element;
import org.wirez.core.client.canvas.CanvasHandler;

public abstract class AbstractCanvasModelListener extends AbstractCanvasListener implements CanvasModelListener {
    
    public AbstractCanvasModelListener(final CanvasHandler canvasHandler) {
        super(canvasHandler);
    }

    @Override
    public void onElementAdded(Element element) {
        
    }

    @Override
    public void onElementModified(Element element) {

    }

    @Override
    public void onElementDeleted(Element element) {

    }
}
