package org.kie.workbench.common.stunner.core.client.canvas.controls.builder.request;

import org.kie.workbench.common.stunner.core.client.canvas.CanvasHandler;
import org.kie.workbench.common.stunner.core.client.canvas.controls.builder.BuildRequest;
import org.kie.workbench.common.stunner.core.client.shape.factory.ShapeFactory;

public interface ElementBuildRequest<H extends CanvasHandler> extends BuildRequest {
    
    Object getDefinition();
    
    ShapeFactory<? ,H, ?> getShapeFactory();
    
}
