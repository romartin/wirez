package org.wirez.core.client.canvas;

import org.uberfire.mvp.Command;
import org.wirez.core.client.shape.view.HasEventHandlers;

public interface Layer<T, S, A> extends HasEventHandlers<T, A> {
    
    T initialize(Object view);
    
    T addShape(S shape);

    T removeShape(S shape);
    
    T draw();
    
    void clear();
    
    String toDataURL();
    
    void onAfterDraw(Command callback);
    
    void destroy();
    
}
