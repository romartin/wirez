package org.wirez.client.lienzo.components.toolbox;

import com.ait.lienzo.client.core.shape.IPrimitive;
import org.wirez.core.client.components.toolbox.ToolboxButton;
import org.wirez.core.client.components.toolbox.event.ToolboxButtonEventHandler;

public class LienzoToolboxButton implements ToolboxButton<IPrimitive<?>> {

    private final IPrimitive<?> icon;
    private ToolboxButtonEventHandler clickHandler;
    private ToolboxButtonEventHandler dragHandler;
    private ToolboxButtonEventHandler mouseEnterHandler;
    private ToolboxButtonEventHandler mouseExitHandler;
    
    public LienzoToolboxButton( final IPrimitive<?> icon ) {
        this.icon = icon;
    }

    @Override
    public IPrimitive<?> getIcon() {
        return icon;
    }

    @Override
    public ToolboxButtonEventHandler getClickHandler() {
        return clickHandler;
    }

    @Override
    public ToolboxButtonEventHandler getDragEndHandler() {
        return dragHandler;
    }

    @Override
    public ToolboxButtonEventHandler getMouseEnterHandler() {
        return mouseEnterHandler;
    }

    @Override
    public ToolboxButtonEventHandler getMouseExitHandler() {
        return mouseExitHandler;
    }

    public LienzoToolboxButton setClickHandler(ToolboxButtonEventHandler clickHandler) {
        this.clickHandler = clickHandler;
        return this;
    }

    public LienzoToolboxButton setDragHandler( final ToolboxButtonEventHandler dragHandler ) {
        this.dragHandler = dragHandler;
        return this;
    }

    public LienzoToolboxButton setMouseEnterHandler( final ToolboxButtonEventHandler mouseEnterHandler ) {
        this.mouseEnterHandler = mouseEnterHandler;
        return this;
    }

    public LienzoToolboxButton setMouseExitHandler( final ToolboxButtonEventHandler mouseExitHandler ) {
        this.mouseExitHandler = mouseExitHandler;
        return this;
    }
    
}
