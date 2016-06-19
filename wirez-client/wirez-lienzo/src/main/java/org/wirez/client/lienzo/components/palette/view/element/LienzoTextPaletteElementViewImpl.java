package org.wirez.client.lienzo.components.palette.view.element;

import com.ait.lienzo.client.core.shape.Text;

public final class LienzoTextPaletteElementViewImpl
        implements LienzoTextPaletteElementView {

    private final Text view;

    public LienzoTextPaletteElementViewImpl( final String text,
                                             final String fontFamily,
                                             final double fontSize) {
        this.view = new Text( text, fontFamily, fontSize );
    }

    @Override
    public Text getView() {
        return view;
    }

}
