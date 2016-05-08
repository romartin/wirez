package org.wirez.core.client.components.toolbox.builder;

import org.wirez.core.client.components.toolbox.Toolbox;
import org.wirez.core.client.components.toolbox.ToolboxButton;
import org.wirez.core.client.components.toolbox.ToolboxButtonGrid;
import org.wirez.core.client.shape.view.ShapeView;

public interface ToolboxBuilder<T, G extends ToolboxButtonGrid, V> {
    
    enum Direction {
        NORTH,
        SOUTH,
        EAST,
        WEST,
        NONE,
        NORTH_EAST,
        SOUTH_EAST,
        SOUTH_WEST,
        NORTH_WEST;
    }
    
    T forView( ShapeView<?> view );
    
    T direction( Direction on, Direction towards );
    
    T grid( G grid );
    
    T add( ToolboxButton<V> button );
    
    Toolbox build();
    
}
