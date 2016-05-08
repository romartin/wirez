package org.wirez.client.lienzo.components.toolbox;

import org.wirez.core.client.components.toolbox.ToolboxButtonGrid;

public class LienzoToolboxButtonGrid implements ToolboxButtonGrid {
    
    private final int padding;
    private final int iconSize;
    private final int rows;
    private final int cols;

    public LienzoToolboxButtonGrid(final int padding, 
                                   final int iconSize, 
                                   final int rows, 
                                   final int cols) {
        this.padding = padding;
        this.iconSize = iconSize;
        this.rows = rows;
        this.cols = cols;
    }

    @Override
    public int getPadding() {
        return padding;
    }

    @Override
    public int getButtonSize() {
        return iconSize;
    }

    @Override
    public int getRows() {
        return rows;
    }

    @Override
    public int getColumns() {
        return cols;
    }
    
}
