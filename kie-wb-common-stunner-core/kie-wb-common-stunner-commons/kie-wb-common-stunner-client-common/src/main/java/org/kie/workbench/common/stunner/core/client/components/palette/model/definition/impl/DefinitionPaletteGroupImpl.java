package org.kie.workbench.common.stunner.core.client.components.palette.model.definition.impl;

import org.kie.workbench.common.stunner.core.client.components.palette.model.AbstractPaletteGroup;
import org.kie.workbench.common.stunner.core.client.components.palette.model.AbstractPaletteGroupBuilder;
import org.kie.workbench.common.stunner.core.client.components.palette.model.definition.DefinitionPaletteGroup;
import org.kie.workbench.common.stunner.core.client.components.palette.model.definition.DefinitionPaletteItem;

import java.util.List;

public final class DefinitionPaletteGroupImpl extends AbstractPaletteGroup<DefinitionPaletteItem> implements DefinitionPaletteGroup {

    private DefinitionPaletteGroupImpl( final String itemId,
                                          final String title,
                                          final String description,
                                          final String tooltip,
                                          final List<DefinitionPaletteItem> items,
                                          final String definitionId ) {

        super( itemId, title, description, tooltip, definitionId, items );

    }

    static class DefinitionPaletteGroupBuilder extends AbstractPaletteGroupBuilder<DefinitionPaletteGroupBuilder,
                DefinitionPaletteGroupImpl, DefinitionPaletteItem> {


        public DefinitionPaletteGroupBuilder( final String id ) {
            super( id );
        }

        @Override
        protected DefinitionPaletteGroupImpl doBuild( final List<DefinitionPaletteItem> items ) {
            return new DefinitionPaletteGroupImpl( id, title, description, tooltip, items, definitionId );
        }

    }

}
