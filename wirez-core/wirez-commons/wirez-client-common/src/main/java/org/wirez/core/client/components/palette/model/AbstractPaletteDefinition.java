package org.wirez.core.client.components.palette.model;

import org.wirez.core.client.components.palette.view.PaletteGrid;

import java.util.List;

public abstract class AbstractPaletteDefinition<I extends PaletteItem> implements PaletteDefinition<I> {

    protected final List<I> items;

    protected AbstractPaletteDefinition( final List<I> groups ) {
        this.items = groups;
    }

    @Override
    public List<I> getItems() {
        return items;
    }

}
