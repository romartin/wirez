package org.wirez.core.client.canvas.controls.toolbox.command;

import com.ait.lienzo.client.core.shape.Shape;
import org.wirez.core.api.graph.Element;

public interface ToolboxCommand {

    enum Status {
        ENABLED, DISABLED;
    }
    
    Shape<?> getIcon();
    
    String getTitle();
    
    // TODO: Status check( Element element );
    
    void execute(Context context, Element element ); 
    
}
