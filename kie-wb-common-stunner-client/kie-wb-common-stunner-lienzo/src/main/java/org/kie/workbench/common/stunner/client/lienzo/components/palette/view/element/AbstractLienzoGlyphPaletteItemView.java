package org.kie.workbench.common.stunner.client.lienzo.components.palette.view.element;

import com.ait.lienzo.client.core.shape.IPrimitive;
import org.kie.workbench.common.stunner.client.lienzo.components.palette.view.LienzoPaletteView;
import org.kie.workbench.common.stunner.core.client.components.palette.model.GlyphPaletteItem;

public abstract class AbstractLienzoGlyphPaletteItemView
        extends AbstractLienzoPaletteItemView<GlyphPaletteItem, IPrimitive<?>>
        implements LienzoGlyphPaletteItemView {


    public AbstractLienzoGlyphPaletteItemView(final GlyphPaletteItem item,
                                              final LienzoPaletteView paletteView) {
        super( item, paletteView );
    }

    @Override
    public Decorator getDecorator() {
        return Decorator.DEFAULT;
    }

}
