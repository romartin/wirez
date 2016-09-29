package org.kie.workbench.common.stunner.client.lienzo.components.palette;

import org.kie.workbench.common.stunner.client.lienzo.components.palette.view.LienzoPaletteViewImpl;
import org.kie.workbench.common.stunner.core.client.components.palette.model.GlyphPaletteItem;
import org.kie.workbench.common.stunner.core.client.components.palette.model.HasPaletteItems;

public interface LienzoGlyphsPalette extends LienzoGlyphItemsPalette<HasPaletteItems<? extends GlyphPaletteItem>, LienzoPaletteViewImpl> {
}
