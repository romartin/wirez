package org.wirez.core.client.control.toolbox.action;

import org.wirez.core.api.graph.Element;
import org.wirez.core.client.Shape;
import org.wirez.core.client.canvas.CanvasHandler;
import org.wirez.lienzo.toolbox.ButtonsOrRegister;

public interface ToolboxCommand {

    Shape<?> getIcon();
    
    void execute(CanvasHandler canvasHandler, final Element element, final Shape shape, final double x, final double y);
    
}
