package org.wirez.client.lienzo.components.palette.view.element;

import org.wirez.client.lienzo.components.palette.view.LienzoPaletteView;
import org.wirez.core.client.components.palette.model.PaletteItem;
import org.wirez.core.client.components.palette.view.AbstractPaletteItemView;
import org.wirez.core.client.components.palette.view.PaletteItemView;

public abstract class AbstractLienzoPaletteItemView<I extends PaletteItem, V>
        extends AbstractPaletteItemView<I, V>
        implements PaletteItemView<I, V> {

    protected final LienzoPaletteView paletteView;

    public AbstractLienzoPaletteItemView(final I item,
                                         final LienzoPaletteView paletteView) {
        super( item );
        this.paletteView = paletteView;
    }

    protected void batch() {

        getPaletteView().draw();

    }

    protected LienzoPaletteView getPaletteView() {
        return paletteView;
    }

}
