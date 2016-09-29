package org.kie.workbench.common.stunner.client.lienzo.components.palette.view.element;

import com.ait.lienzo.client.core.shape.IPrimitive;
import com.ait.lienzo.client.core.shape.Rectangle;

// TODO: Review alphas workaround.
public final class LienzoSeparatorPaletteElementViewImpl
        implements LienzoSeparatorPaletteElementView {

    private final Rectangle view;

    public LienzoSeparatorPaletteElementViewImpl( final double height,
                                                  final double width) {
        this.view = new Rectangle( width, height ).setAlpha( 0.1d ).setStrokeAlpha( 0-1d );
    }

    @Override
    public IPrimitive<?> getView() {
        return view;
    }

}
