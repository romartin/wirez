package org.wirez.core.client.canvas;

import org.uberfire.mvp.Command;
import org.wirez.core.client.shape.view.HasEventHandlers;

public interface Layer<T, S> extends HasEventHandlers<T> {
    
    T initialize(Object view);
    
    T addShape(S shape);

    T removeShape(S shape);
    
    T draw();
    
    void clear();
    
    String toDataURL();
    
    void onBeforeDraw( Command callback );

    void onAfterDraw( Command callback );
}
