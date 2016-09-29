package org.kie.workbench.common.stunner.core.client.components.palette.model.definition.impl;

import org.kie.workbench.common.stunner.core.client.components.palette.model.AbstractPaletteDefinition;
import org.kie.workbench.common.stunner.core.client.components.palette.model.definition.DefinitionPaletteCategory;
import org.kie.workbench.common.stunner.core.client.components.palette.model.definition.DefinitionSetPalette;

import java.util.List;

public final class DefinitionSetPaletteImpl
        extends AbstractPaletteDefinition<DefinitionPaletteCategory>
        implements DefinitionSetPalette {

    private final String defSetId;

    protected DefinitionSetPaletteImpl( final List<DefinitionPaletteCategory> categories, String defSetId ) {
        super( categories );
        this.defSetId = defSetId;
    }


    @Override
    public String getDefinitionSetId() {
        return defSetId;
    }
}
