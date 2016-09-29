package org.kie.workbench.common.stunner.core.client.components.palette.view;

public class PaletteGridBuilder extends AbstractPaletteGridBuilder<PaletteGridBuilder> {

    public PaletteGrid build() {

        return new PaletteGridImpl( rows, columns, iconSize, padding );

    }

}
