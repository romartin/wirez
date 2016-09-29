package org.kie.workbench.common.stunner.core.client.components.palette.model.definition.impl;

import org.kie.workbench.common.stunner.core.client.components.palette.model.AbstractPaletteItem;
import org.kie.workbench.common.stunner.core.client.components.palette.model.AbstractPaletteItemBuilder;
import org.kie.workbench.common.stunner.core.client.components.palette.model.definition.DefinitionPaletteItem;

public final class DefinitionPaletteItemImpl
        extends AbstractPaletteItem
        implements DefinitionPaletteItem {

    private final String definitionId;

    private DefinitionPaletteItemImpl(final String itemId,
                                      final String title,
                                      final String description,
                                      final String tooltip,
                                      final String definitionId) {
        super(itemId, title, description, tooltip);
        this.definitionId = definitionId;
    }

    public String getDefinitionId() {
        return definitionId;
    }

    static class DefinitionPaletteItemBuilder
            extends AbstractPaletteItemBuilder<DefinitionPaletteItemBuilder, DefinitionPaletteItemImpl> {

        private String definitionId;

        public DefinitionPaletteItemBuilder( final String id ) {
            super( id );
        }

        public DefinitionPaletteItemBuilder definitionId( final String definitionId ) {
            this.definitionId = definitionId;
            return this;
        }

        @Override
        public DefinitionPaletteItemImpl build() {
            return new DefinitionPaletteItemImpl( id, title, description, tooltip, definitionId );
        }

    }

}
