package org.wirez.client.lienzo.components.palette.view;

import org.wirez.client.lienzo.components.palette.view.element.LienzoPaletteElementView;
import org.wirez.lienzo.palette.AbstractPalette;
import org.wirez.lienzo.palette.Palette;

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
