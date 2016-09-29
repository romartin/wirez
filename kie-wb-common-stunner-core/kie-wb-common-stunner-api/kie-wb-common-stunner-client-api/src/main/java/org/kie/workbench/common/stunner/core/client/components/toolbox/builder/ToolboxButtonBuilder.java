package org.kie.workbench.common.stunner.core.client.components.toolbox.builder;

import org.kie.workbench.common.stunner.core.client.components.toolbox.ToolboxButton;
import org.kie.workbench.common.stunner.core.client.components.toolbox.event.ToolboxButtonEventHandler;

public interface ToolboxButtonBuilder<I> {

    ToolboxButtonBuilder<I> setIcon( I icon );

    ToolboxButtonBuilder<I> setHoverAnimation( ToolboxButton.HoverAnimation animation );

    ToolboxButtonBuilder<I> setClickHandler( ToolboxButtonEventHandler handler );

    ToolboxButtonBuilder<I> setMouseDownHandler( ToolboxButtonEventHandler handler );

    ToolboxButtonBuilder<I> setMouseEnterHandler( ToolboxButtonEventHandler handler );

    ToolboxButtonBuilder<I> setMouseExitHandler( ToolboxButtonEventHandler handler );

    ToolboxButton<I> build();
    
}
