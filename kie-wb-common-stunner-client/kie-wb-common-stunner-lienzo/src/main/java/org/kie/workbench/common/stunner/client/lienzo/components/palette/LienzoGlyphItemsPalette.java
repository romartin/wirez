package org.kie.workbench.common.stunner.client.lienzo.components.palette;

import org.kie.workbench.common.stunner.client.lienzo.components.palette.view.LienzoPaletteView;
import org.kie.workbench.common.stunner.core.client.components.glyph.DefinitionGlyphTooltip;
import org.kie.workbench.common.stunner.core.client.components.palette.model.GlyphPaletteItem;
import org.kie.workbench.common.stunner.core.client.components.palette.model.HasPaletteItems;

import java.util.List;

public interface LienzoGlyphItemsPalette<I extends HasPaletteItems<? extends GlyphPaletteItem>, V extends LienzoPaletteView>
        extends LienzoPalette<I, V> {

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

    LienzoGlyphItemsPalette<I, V> onShowGlyTooltip( GlyphTooltipCallback callback );


}
