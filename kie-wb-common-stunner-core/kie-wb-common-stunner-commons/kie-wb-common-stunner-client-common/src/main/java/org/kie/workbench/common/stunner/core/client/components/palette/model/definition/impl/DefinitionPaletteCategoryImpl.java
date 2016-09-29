package org.kie.workbench.common.stunner.core.client.components.palette.model.definition.impl;

import org.kie.workbench.common.stunner.core.client.components.palette.model.AbstractPaletteGroup;
import org.kie.workbench.common.stunner.core.client.components.palette.model.AbstractPaletteGroupBuilder;
import org.kie.workbench.common.stunner.core.client.components.palette.model.definition.DefinitionPaletteCategory;
import org.kie.workbench.common.stunner.core.client.components.palette.model.definition.DefinitionPaletteItem;

import java.util.List;

public final class DefinitionPaletteCategoryImpl extends AbstractPaletteGroup<DefinitionPaletteItem> implements DefinitionPaletteCategory {

    private DefinitionPaletteCategoryImpl(final String itemId,
                                            final String title,
                                            final String description,
                                            final String tooltip,
                                            final String glyphDefinitionId,
                                            final List<DefinitionPaletteItem> items ) {

        super( itemId, title, description, tooltip, glyphDefinitionId, items );

    }

    static class DefinitionPaletteCategoryBuilder extends AbstractPaletteGroupBuilder<DefinitionPaletteCategoryBuilder,
                DefinitionPaletteCategoryImpl, DefinitionPaletteItem> {

        public DefinitionPaletteCategoryBuilder( final String id ) {
            super( id );
        }

        @Override
        protected DefinitionPaletteCategoryImpl doBuild( final List<DefinitionPaletteItem> items ) {

            if ( null == definitionId && !items.isEmpty() ) {

                final DefinitionPaletteItem item = items.get( 0 );
                this.definitionId = item.getDefinitionId();

            }

            return new DefinitionPaletteCategoryImpl( id, title, description, tooltip, definitionId, items );
        }

    }

}
