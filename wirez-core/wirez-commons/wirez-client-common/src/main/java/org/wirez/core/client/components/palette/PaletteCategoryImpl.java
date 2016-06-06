package org.wirez.core.client.components.palette;

public final class PaletteCategoryImpl<V> implements PaletteCategory<V> {

    private final String title;
    private final PaletteGrid grid;
    private final V[] items;

    public PaletteCategoryImpl(final String title,
                               final PaletteGrid grid,
                               final V[] items) {
        this.title = title;
        this.grid = grid;
        this.items = items;
    }


    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public PaletteGrid getGrid() {
        return grid;
    }

    @Override
    public V[] getItems() {
        return items;
    }

}
