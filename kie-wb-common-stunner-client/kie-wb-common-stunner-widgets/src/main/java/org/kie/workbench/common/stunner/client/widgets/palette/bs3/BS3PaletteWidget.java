package org.kie.workbench.common.stunner.client.widgets.palette.bs3;

import org.kie.workbench.common.stunner.client.widgets.palette.PaletteWidget;
import org.kie.workbench.common.stunner.client.widgets.palette.bs3.factory.BS3PaletteViewFactory;
import org.kie.workbench.common.stunner.core.client.components.palette.model.definition.DefinitionSetPalette;

public interface BS3PaletteWidget extends PaletteWidget<DefinitionSetPalette, BS3PaletteWidgetView> {

    BS3PaletteWidget setViewFactory( BS3PaletteViewFactory viewFactory );

}
