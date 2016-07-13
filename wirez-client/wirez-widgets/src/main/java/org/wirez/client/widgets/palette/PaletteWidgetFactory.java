package org.wirez.client.widgets.palette;

import org.wirez.core.client.canvas.CanvasHandler;
import org.wirez.core.client.components.palette.factory.PaletteFactory;
import org.wirez.core.client.components.palette.model.PaletteDefinition;

public interface PaletteWidgetFactory<I extends PaletteDefinition, P extends PaletteWidget<I, ?>> extends PaletteFactory<I, P> {

    PaletteWidgetFactory<I, P> forCanvasHandler( CanvasHandler canvasHandler );

}
