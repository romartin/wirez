package org.wirez.core.client.canvas.listener;

import org.wirez.core.api.graph.Element;

public interface CanvasModelListener extends CanvasListener {

    void onElementAdded(Element element);

    void onElementModified(Element element);

    void onElementDeleted(Element element);
    
}
