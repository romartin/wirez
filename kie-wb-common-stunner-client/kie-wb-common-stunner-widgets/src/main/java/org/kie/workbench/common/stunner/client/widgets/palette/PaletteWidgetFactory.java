package org.kie.workbench.common.stunner.client.widgets.palette;

import org.kie.workbench.common.stunner.core.client.canvas.CanvasHandler;
import org.kie.workbench.common.stunner.core.client.components.palette.factory.PaletteFactory;
import org.kie.workbench.common.stunner.core.client.components.palette.model.PaletteDefinition;

public interface PaletteWidgetFactory<I extends PaletteDefinition, P extends PaletteWidget<I, ?>> extends PaletteFactory<I, P> {

    PaletteWidgetFactory<I, P> forCanvasHandler( CanvasHandler canvasHandler );

}
