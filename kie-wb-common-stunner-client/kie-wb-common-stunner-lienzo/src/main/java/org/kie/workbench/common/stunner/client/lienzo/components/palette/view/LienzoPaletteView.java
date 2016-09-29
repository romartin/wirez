package org.kie.workbench.common.stunner.client.lienzo.components.palette.view;

import com.ait.lienzo.client.core.shape.Layer;
import org.kie.workbench.common.stunner.client.lienzo.components.palette.AbstractLienzoPalette;
import org.kie.workbench.common.stunner.client.lienzo.components.palette.view.element.LienzoPaletteElementView;
import org.kie.workbench.common.stunner.core.client.components.palette.view.PaletteView;

public interface LienzoPaletteView<V extends LienzoPaletteView, I extends LienzoPaletteElementView>
        extends PaletteView<V, Layer, I> {

    void setPresenter( AbstractLienzoPalette palette );

    void draw();

}
