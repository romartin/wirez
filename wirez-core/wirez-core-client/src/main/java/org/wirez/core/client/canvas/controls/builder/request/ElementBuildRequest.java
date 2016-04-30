package org.wirez.core.client.canvas.controls.builder.request;

import org.wirez.core.client.canvas.CanvasHandler;
import org.wirez.core.client.canvas.controls.builder.BuildRequest;
import org.wirez.core.client.shape.factory.ShapeFactory;

public interface ElementBuildRequest<H extends CanvasHandler> extends BuildRequest {
    
    Object getDefinition();
    
    ShapeFactory<? ,H, ?> getShapeFactory();
    
}
