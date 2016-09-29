package org.kie.workbench.common.stunner.core.client.canvas.listener;

import org.kie.workbench.common.stunner.core.client.canvas.CanvasHandler;
import org.kie.workbench.common.stunner.core.graph.Element;

public interface CanvasElementListener extends CanvasListener<CanvasHandler, Element> {

    /**
     * An element has been updated on the canvas.
     */
    void update( Element item );

}
