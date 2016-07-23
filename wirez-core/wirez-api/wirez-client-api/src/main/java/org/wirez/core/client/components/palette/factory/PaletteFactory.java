package org.wirez.core.client.components.palette.factory;

import org.wirez.core.client.components.palette.Palette;
import org.wirez.core.client.components.palette.model.HasPaletteItems;
import org.wirez.core.client.components.palette.view.PaletteGrid;

public interface PaletteFactory<I extends HasPaletteItems, P extends Palette<I>> {

    /**
     * Builds a new palette for the given shape set identifier.
     */
    P newPalette( String shapeSetId, PaletteGrid grid );

}
