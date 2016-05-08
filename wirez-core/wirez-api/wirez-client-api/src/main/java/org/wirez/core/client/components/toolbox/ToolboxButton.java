package org.wirez.core.client.components.toolbox;

import org.wirez.core.client.components.toolbox.event.ToolboxButtonEventHandler;

public interface ToolboxButton<I> {
    
    I getIcon();

    ToolboxButtonEventHandler getClickHandler();

    ToolboxButtonEventHandler getDragEndHandler();

    ToolboxButtonEventHandler getMouseEnterHandler();

    ToolboxButtonEventHandler getMouseExitHandler();
    
}
