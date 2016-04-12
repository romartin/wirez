package org.wirez.core.client.control;

import org.wirez.core.api.graph.Element;
import org.wirez.core.client.Shape;
import org.wirez.core.client.canvas.command.factory.CanvasCommandFactory;

import javax.inject.Inject;

public abstract class BaseDragControl<S extends Shape, E extends Element> extends BaseShapeControl<S, E> {
    
    @Inject
    public BaseDragControl(final CanvasCommandFactory canvasCommandFactory) {
        super( canvasCommandFactory );
    }
    
}
