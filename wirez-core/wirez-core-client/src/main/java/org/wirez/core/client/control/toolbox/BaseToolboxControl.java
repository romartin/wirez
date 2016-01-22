package org.wirez.core.client.control.toolbox;

import org.wirez.core.api.graph.Element;
import org.wirez.core.client.Shape;
import org.wirez.core.client.canvas.command.impl.DefaultCanvasCommands;
import org.wirez.core.client.control.BaseShapeControl;

public abstract class BaseToolboxControl<S extends Shape, E extends Element> extends BaseShapeControl<S, E> {
    
    public BaseToolboxControl(DefaultCanvasCommands defaultCanvasCommands) {
        super(defaultCanvasCommands);
    }
    
}
