package org.kie.workbench.common.stunner.core.client.components.palette.view;

import org.kie.workbench.common.stunner.core.client.components.palette.view.PaletteGrid;

public final class PaletteGridImpl implements PaletteGrid {

    private final int rows;
    private final int cols;
    private final int iconSize;
    private final int padding;

    public PaletteGridImpl(final int iconSize,
                           final int padding) {
        this( -1, -1, iconSize, padding );
    }

    public PaletteGridImpl(final int rows,
                           final int cols,
                           final int iconSize,
                           final int padding) {
        this.rows = rows;
        this.cols = cols;
        this.iconSize = iconSize;
        this.padding = padding;
    }

    @Override
    public int getRows() {
        return rows;
    }

    @Override
    public int getColumns() {
        return cols;
    }

    @Override
    public int getIconSize() {
        return iconSize;
    }

    @Override
    public int getPadding() {
        return padding;
    }

}