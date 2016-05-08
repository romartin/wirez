package org.wirez.core.client.components.toolbox.builder;

import org.wirez.core.client.components.toolbox.ToolboxButtonGrid;

public interface ToolboxButtonGridBuilder {

    ToolboxButtonGridBuilder setPadding( int padding );

    ToolboxButtonGridBuilder setIconSize( int iconSize );

    ToolboxButtonGridBuilder setRows( int rows );

    ToolboxButtonGridBuilder setColumns( int cols );
    
    ToolboxButtonGrid build();
    
}
