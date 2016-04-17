package org.wirez.core.client.canvas.controls.toolbox.command;

import com.ait.lienzo.client.core.shape.Shape;
import org.wirez.core.api.graph.Element;

public interface ToolboxCommand {

    Shape<?> getIcon();
    
    String getTitle();
    
    void execute(Context context, Element element);
    
}
