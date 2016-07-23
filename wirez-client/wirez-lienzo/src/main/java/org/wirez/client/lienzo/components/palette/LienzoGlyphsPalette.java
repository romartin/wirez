package org.wirez.client.lienzo.components.palette;

import org.wirez.client.lienzo.components.palette.view.LienzoPaletteViewImpl;
import org.wirez.core.client.components.palette.model.GlyphPaletteItem;
import org.wirez.core.client.components.palette.model.HasPaletteItems;

public interface LienzoGlyphsPalette extends LienzoGlyphItemsPalette<HasPaletteItems<? extends GlyphPaletteItem>, LienzoPaletteViewImpl> {
}
