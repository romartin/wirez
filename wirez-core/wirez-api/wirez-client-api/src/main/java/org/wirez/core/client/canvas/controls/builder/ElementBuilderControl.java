package org.wirez.core.client.canvas.controls.builder;

import org.wirez.core.client.canvas.CanvasHandler;
import org.wirez.core.client.canvas.controls.builder.request.ElementBuildRequest;

public interface ElementBuilderControl<C extends CanvasHandler> extends BuilderControl<C, ElementBuildRequest<C>> {
    
}
