package org.wirez.lienzo.toolbox.builder;


import org.wirez.lienzo.toolbox.ToolboxButton;
import org.wirez.lienzo.toolbox.event.ToolboxButtonEventHandler;

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
