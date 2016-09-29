package org.kie.workbench.common.stunner.core.client.components.palette.model.definition.impl;

import org.kie.workbench.common.stunner.core.client.components.palette.model.definition.DefinitionsPalette;
import org.kie.workbench.common.stunner.core.client.components.palette.model.AbstractPaletteDefinition;
import org.kie.workbench.common.stunner.core.client.components.palette.model.definition.DefinitionPaletteItem;

import java.util.List;

public final class DefinitionsPaletteImpl
        extends AbstractPaletteDefinition<DefinitionPaletteItem>
        implements DefinitionsPalette {

    protected DefinitionsPaletteImpl( final List<DefinitionPaletteItem> items ) {
        super( items );
    }

}
