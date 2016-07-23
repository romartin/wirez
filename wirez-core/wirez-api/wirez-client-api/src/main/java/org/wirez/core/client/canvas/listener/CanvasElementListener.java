package org.wirez.core.client.canvas.listener;

import org.wirez.core.client.canvas.CanvasHandler;
import org.wirez.core.graph.Element;

public interface CanvasElementListener extends CanvasListener<CanvasHandler, Element> {

    /**
     * An element has been updated on the canvas.
     */
    void update( Element item );

}
