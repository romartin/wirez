package org.kie.workbench.common.stunner.core.client.components.palette.factory;

import org.kie.workbench.common.stunner.core.client.components.palette.model.PaletteDefinitionBuilder;

public interface PaletteDefinitionFactory<B extends PaletteDefinitionBuilder> {

    /**
     * Returns if this provider accepts the given Definition Set identifier.
     */
    boolean accepts( String defSetId );

    /**
     * Builds the palette definition for the given Definition Set identifier.
     */
    B newBuilder( String defSetId );

}
