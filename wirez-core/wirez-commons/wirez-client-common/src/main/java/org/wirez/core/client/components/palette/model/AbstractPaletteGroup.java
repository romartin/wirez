package org.wirez.core.client.components.palette.model;

import org.wirez.core.client.components.palette.view.PaletteGrid;

import java.util.List;

public abstract class AbstractPaletteGroup<I extends PaletteItem>
        extends AbstractPaletteItem
        implements PaletteGroup<I> {

    protected final String definitionId;
    protected final List<I> items;

    public AbstractPaletteGroup(final String itemId,
                                final String title,
                                final String description,
                                final String tooltip,
                                final String definitionId,
                                final List<I> items) {
        super( itemId, title, description, tooltip );
        this.definitionId = definitionId;
        this.items = items;
    }

    @Override
    public List<I> getItems() {
        return items;
    }

    public String getDefinitionId() {
        return definitionId;
    }

}
