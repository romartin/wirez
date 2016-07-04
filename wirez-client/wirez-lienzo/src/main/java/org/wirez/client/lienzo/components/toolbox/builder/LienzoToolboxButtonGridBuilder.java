package org.wirez.client.lienzo.components.toolbox.builder;

import org.wirez.client.lienzo.components.toolbox.LienzoToolboxButtonGrid;
import org.wirez.core.client.components.toolbox.ToolboxButtonGrid;
import org.wirez.core.client.components.toolbox.builder.ToolboxButtonGridBuilder;

public class LienzoToolboxButtonGridBuilder implements ToolboxButtonGridBuilder {

    public static final int PADDING = 5;
    public static final int ICON_SIZE = 12;
    
    private int padding = PADDING;
    private int iconSize = ICON_SIZE;
    private int rows;
    private int cols;
    
    @Override
    public ToolboxButtonGridBuilder setPadding( final int padding ) {
        this.padding = padding;
        return this;
    }

    @Override
    public ToolboxButtonGridBuilder setIconSize( final int iconSize ) {
        this.iconSize = iconSize;
        return this;
    }

    @Override
    public ToolboxButtonGridBuilder setRows( final int rows ) {
        this.rows = rows;
        return this;
    }

    @Override
    public ToolboxButtonGridBuilder setColumns( final int cols ) {
        this.cols = cols;
        return this;
    }

    @Override
    public ToolboxButtonGrid build() {
        return new LienzoToolboxButtonGrid( padding, iconSize, rows, cols );
    }
    
}
