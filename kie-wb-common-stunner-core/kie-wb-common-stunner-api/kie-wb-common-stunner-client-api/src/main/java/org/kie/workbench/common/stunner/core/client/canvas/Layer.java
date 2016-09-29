package org.kie.workbench.common.stunner.core.client.canvas;

import org.uberfire.mvp.Command;
import org.kie.workbench.common.stunner.core.client.shape.view.HasEventHandlers;

public interface Layer<T, S, A> extends HasEventHandlers<T, A> {
    
    T initialize(Object view);
    
    T addShape(S shape);

    T removeShape(S shape);
    
    T draw();
    
    void clear();
    
    String toDataURL();

    String toDataURL( int x, int y, int width, int height );
    
    void onAfterDraw(Command callback);
    
    void destroy();
    
}
