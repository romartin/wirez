package org.wirez.core.client.components.palette.model.definition.impl;

import org.wirez.core.client.components.palette.model.AbstractPaletteDefinition;
import org.wirez.core.client.components.palette.model.definition.DefinitionPaletteItem;
import org.wirez.core.client.components.palette.model.definition.DefinitionsPalette;

import java.util.List;

public final class DefinitionsPaletteImpl
        extends AbstractPaletteDefinition<DefinitionPaletteItem>
        implements DefinitionsPalette {

    protected DefinitionsPaletteImpl( final List<DefinitionPaletteItem> items ) {
        super( items );
    }

}
