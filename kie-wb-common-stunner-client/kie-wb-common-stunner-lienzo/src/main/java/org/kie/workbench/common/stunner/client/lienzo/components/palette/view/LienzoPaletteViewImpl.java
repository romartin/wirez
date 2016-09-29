package org.kie.workbench.common.stunner.client.lienzo.components.palette.view;

import org.kie.workbench.common.stunner.client.lienzo.components.palette.view.element.LienzoPaletteElementView;
import org.kie.workbench.common.stunner.lienzo.palette.AbstractPalette;
import org.kie.workbench.common.stunner.lienzo.palette.Palette;

import javax.enterprise.context.Dependent;

@Dependent
public class LienzoPaletteViewImpl
        extends AbstractLienzoPaletteView<LienzoPaletteViewImpl>
        implements LienzoPaletteView<LienzoPaletteViewImpl, LienzoPaletteElementView> {

    @Override
    protected AbstractPalette<? extends AbstractPalette> buildPalette() {
        return new Palette();
    }

}
