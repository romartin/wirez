package org.wirez.client.lienzo.components.palette;

import org.wirez.client.lienzo.components.palette.view.LienzoPaletteView;
import org.wirez.core.client.components.glyph.DefinitionGlyphTooltip;
import org.wirez.core.client.components.palette.model.GlyphPaletteItem;
import org.wirez.core.client.components.palette.model.HasPaletteItems;

import java.util.List;

public interface LienzoGlyphItemsPalette<V extends LienzoPaletteView>
        extends LienzoPalette<HasPaletteItems<? extends GlyphPaletteItem>, V> {

    interface GlyphTooltipCallback {

        boolean onShowTooltip(DefinitionGlyphTooltip<?> glyphTooltip,
                              final GlyphPaletteItem item,
                              double mouseX,
                              double mouseY,
                              double itemX,
                              double itemY);

    }

    List<GlyphPaletteItem> getItems();

    GlyphPaletteItem getItem( int pos );

    LienzoGlyphItemsPalette<V> onShowGlyTooltip( GlyphTooltipCallback callback );


}
