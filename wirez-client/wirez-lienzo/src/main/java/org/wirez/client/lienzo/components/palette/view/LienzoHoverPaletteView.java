package org.wirez.client.lienzo.components.palette.view;

import org.wirez.client.lienzo.components.palette.view.element.LienzoPaletteElementView;
import org.wirez.lienzo.palette.HoverPalette;

import javax.enterprise.context.Dependent;

@Dependent
public class LienzoHoverPaletteView
        extends AbstractLienzoPaletteView<LienzoHoverPaletteView>
        implements LienzoPaletteView<LienzoHoverPaletteView, LienzoPaletteElementView> {

    @Override
    protected HoverPalette buildPalette() {
        return new HoverPalette().setTimeout( 1500 );
    }

    @Override
    protected void initPaletteCallbacks() {

        super.initPaletteCallbacks();

        if ( null != getHoverPalette() ) {

            getHoverPalette().setCloseCallback( () -> presenter.onClose() );

        }

    }

    @Override
    public void destroy() {

        if ( null != getHoverPalette() ) {

            getHoverPalette().setCloseCallback( null );

        }

        super.destroy();

    }

    private HoverPalette getHoverPalette() {
        return null != getPalette() ? (HoverPalette) getPalette() : null;
    }

}
