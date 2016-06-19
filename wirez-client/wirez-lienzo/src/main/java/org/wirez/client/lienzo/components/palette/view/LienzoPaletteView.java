package org.wirez.client.lienzo.components.palette.view;

import com.ait.lienzo.client.core.shape.Layer;
import org.wirez.client.lienzo.components.palette.AbstractLienzoPalette;
import org.wirez.client.lienzo.components.palette.view.element.LienzoPaletteElementView;
import org.wirez.core.client.components.palette.view.PaletteGrid;
import org.wirez.core.client.components.palette.view.PaletteView;

public interface LienzoPaletteView<V extends LienzoPaletteView, I extends LienzoPaletteElementView>
        extends PaletteView<V, Layer, I> {

    void setPresenter( AbstractLienzoPalette palette );

    void setGrid( PaletteGrid grid );

    void draw();

}
