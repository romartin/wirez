package org.kie.workbench.common.stunner.lienzo.toolbox.builder;


import org.kie.workbench.common.stunner.lienzo.toolbox.ToolboxButton;
import org.kie.workbench.common.stunner.lienzo.toolbox.event.ToolboxButtonEventHandler;

public interface Button {
    interface WhenReady {
        void whenReady(ToolboxButton button);
    }

    Button setAnimation( ToolboxButton.HoverAnimation animation );

    Button setClickHandler(ToolboxButtonEventHandler handler);

    Button setMouseDownHandler(ToolboxButtonEventHandler handler);

    Button setMouseEnterHandler(ToolboxButtonEventHandler handler);

    Button setMouseExitHandler(ToolboxButtonEventHandler handler);
    
    Button whenReady(WhenReady callback);

    ButtonsOrRegister end();

}
