package org.kie.workbench.common.stunner.core.client.components.palette.factory;

import org.kie.workbench.common.stunner.core.client.components.palette.model.HasPaletteItems;
import org.kie.workbench.common.stunner.core.client.components.palette.view.PaletteGrid;
import org.kie.workbench.common.stunner.core.client.components.palette.Palette;

public interface PaletteFactory<I extends HasPaletteItems, P extends Palette<I>> {

    /**
     * Builds a new palette for the given shape set identifier.
     */
    P newPalette( String shapeSetId, PaletteGrid grid );

}
