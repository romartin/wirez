package org.kie.workbench.common.stunner.core.client.components.toolbox;

import org.kie.workbench.common.stunner.core.client.components.toolbox.event.ToolboxButtonEventHandler;

public interface ToolboxButton<I> {

    public enum HoverAnimation {

        ELASTIC, HOVER_COLOR;

    }

    I getIcon();

    HoverAnimation getAnimation();

    ToolboxButtonEventHandler getClickHandler();

    ToolboxButtonEventHandler getMouseDownHandler();

    ToolboxButtonEventHandler getMouseEnterHandler();

    ToolboxButtonEventHandler getMouseExitHandler();
    
}
