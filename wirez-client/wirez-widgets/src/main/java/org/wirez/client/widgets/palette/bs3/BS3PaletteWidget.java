package org.wirez.client.widgets.palette.bs3;

import org.wirez.client.widgets.palette.PaletteWidget;
import org.wirez.client.widgets.palette.bs3.factory.BS3PaletteViewFactory;
import org.wirez.core.client.components.palette.model.definition.DefinitionSetPalette;

public interface BS3PaletteWidget extends PaletteWidget<DefinitionSetPalette, BS3PaletteWidgetView> {

    BS3PaletteWidget setViewFactory( BS3PaletteViewFactory viewFactory );

}
