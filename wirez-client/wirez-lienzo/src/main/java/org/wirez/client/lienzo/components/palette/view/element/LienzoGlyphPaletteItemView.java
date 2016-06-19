package org.wirez.client.lienzo.components.palette.view.element;

import com.ait.lienzo.client.core.shape.IPrimitive;
import org.wirez.core.client.components.palette.model.GlyphPaletteItem;
import org.wirez.core.client.components.palette.view.PaletteItemView;

public interface LienzoGlyphPaletteItemView extends PaletteItemView<GlyphPaletteItem, IPrimitive<?>>, LienzoPaletteElementView {

    enum Decorator {

        DEFAULT;

    }

    Decorator getDecorator();

    void expand();

    void collapse();

}
