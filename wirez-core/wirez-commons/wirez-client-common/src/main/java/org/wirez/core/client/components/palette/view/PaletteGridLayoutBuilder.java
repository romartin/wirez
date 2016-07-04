package org.wirez.core.client.components.palette.view;

public class PaletteGridLayoutBuilder extends AbstractPaletteGridBuilder<PaletteGridLayoutBuilder> {

    public static final PaletteGridLayoutBuilder HORIZONTAL = new PaletteGridLayoutBuilder().layout( GridLayout.HORIZONTAL );
    public static final PaletteGridLayoutBuilder VERTICAL = new PaletteGridLayoutBuilder().layout( GridLayout.VERTICAL );

    public enum GridLayout {

        HORIZONTAL, VERTICAL;

    }

    protected GridLayout layout = GridLayout.HORIZONTAL;

    public PaletteGridLayoutBuilder layout(final GridLayout layout ) {
        this.layout = layout;
        return this;
    }

    @Override
    public PaletteGrid build() {

        final int _r  = ( layout.equals( GridLayout.HORIZONTAL ) ) ? 1 : -1;
        final int _c  = ( layout.equals( GridLayout.VERTICAL ) ) ? 1 : -1;

        return new PaletteGridImpl( _r, _c, iconSize, padding );

    }

}
