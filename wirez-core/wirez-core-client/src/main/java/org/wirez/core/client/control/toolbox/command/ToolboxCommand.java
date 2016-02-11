package org.wirez.core.client.control.toolbox.command;

import com.ait.lienzo.client.core.shape.Shape;
import org.wirez.core.api.graph.Element;

public interface ToolboxCommand {

    Shape<?> getIcon();
    
    void execute(Context context, Element element);
    
}
