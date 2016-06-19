package org.wirez.core.client.components.palette.model.definition;

import org.wirez.core.client.components.palette.model.PaletteDefinition;

/**
 * A multi level palette that provides the following grouping strategy:
 * 1.- categories
 * 2.- groups ( by morphing base )
 * 3.- definition palette items for each parent level ( category or group ).
 */
public interface DefinitionSetPalette extends PaletteDefinition<DefinitionPaletteCategory> {

}
