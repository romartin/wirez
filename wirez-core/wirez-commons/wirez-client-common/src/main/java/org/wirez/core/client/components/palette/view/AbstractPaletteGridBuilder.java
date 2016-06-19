package org.wirez.core.client.components.palette.view;

import org.wirez.core.client.components.palette.view.PaletteGrid;

public abstract class AbstractPaletteGridBuilder<B> {

    protected int rows;
    protected int columns;
    protected int iconSize;
    protected int padding;

    protected abstract PaletteGrid build();

    @SuppressWarnings("unchecked")
    public B setRows(int rows) {
        this.rows = rows;
        return (B) this;
    }

    @SuppressWarnings("unchecked")
    public B setColumns(int columns) {
        this.columns = columns;
        return (B) this;
    }

    @SuppressWarnings("unchecked")
    public B setIconSize(int iconSize) {
        this.iconSize = iconSize;
        return (B) this;
    }

    @SuppressWarnings("unchecked")
    public B setPadding(int padding) {
        this.padding = padding;
        return (B) this;
    }

}
