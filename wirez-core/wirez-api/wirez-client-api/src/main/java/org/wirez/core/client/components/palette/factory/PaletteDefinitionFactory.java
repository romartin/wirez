package org.wirez.core.client.components.palette.factory;

import org.wirez.core.client.components.palette.model.HasPaletteItems;
import org.wirez.core.client.components.palette.model.PaletteDefinitionBuilder;
import org.wirez.core.client.service.ClientRuntimeError;

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
