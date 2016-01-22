package org.wirez.core.client.control;

import org.wirez.core.api.graph.Element;
import org.wirez.core.client.Shape;
import org.wirez.core.client.canvas.command.impl.DefaultCanvasCommands;
import org.wirez.core.client.impl.BaseShape;

public abstract class BaseDragControl<S extends Shape, E extends Element> extends BaseShapeControl<S, E> {
    
    public BaseDragControl(DefaultCanvasCommands defaultCanvasCommands) {
        super(defaultCanvasCommands);
    }
    
}
