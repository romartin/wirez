package org.wirez.core.client.canvas;

import org.wirez.core.client.view.HasEventHandlers;

public interface Layer<T, S> extends HasEventHandlers<T> {
    
    T addShape(S shape);

    T removeShape(S shape);
    
}
