package org.kie.workbench.common.stunner.client.lienzo.components.palette.view.element;

import com.ait.lienzo.client.core.shape.IPrimitive;
import org.kie.workbench.common.stunner.core.client.components.palette.model.GlyphPaletteItem;
import org.kie.workbench.common.stunner.core.client.components.palette.view.PaletteItemView;

public interface LienzoGlyphPaletteItemView extends PaletteItemView<GlyphPaletteItem, IPrimitive<?>>, LienzoPaletteElementView {

    enum Decorator {

        DEFAULT;

    }

    Decorator getDecorator();

    void expand();

    void collapse();

}
