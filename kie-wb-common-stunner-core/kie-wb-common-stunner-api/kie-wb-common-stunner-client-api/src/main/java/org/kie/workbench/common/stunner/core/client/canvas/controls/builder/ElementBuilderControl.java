package org.kie.workbench.common.stunner.core.client.canvas.controls.builder;

import org.kie.workbench.common.stunner.core.client.canvas.CanvasHandler;
import org.kie.workbench.common.stunner.core.client.canvas.controls.builder.request.ElementBuildRequest;

public interface ElementBuilderControl<C extends CanvasHandler> extends BuilderControl<C, ElementBuildRequest<C>> {
    
}
