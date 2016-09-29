package org.kie.workbench.common.stunner.client.lienzo.components.toolbox;

import com.ait.lienzo.client.core.shape.IPrimitive;
import org.kie.workbench.common.stunner.core.client.components.toolbox.ToolboxButton;
import org.kie.workbench.common.stunner.core.client.components.toolbox.event.ToolboxButtonEventHandler;

public class LienzoToolboxButton implements ToolboxButton<IPrimitive<?>> {

    private final IPrimitive<?> icon;

    private ToolboxButtonEventHandler clickHandler;
    private ToolboxButtonEventHandler mouseDownHandler;
    private ToolboxButtonEventHandler mouseEnterHandler;
    private ToolboxButtonEventHandler mouseExitHandler;
    private HoverAnimation animation;

    public LienzoToolboxButton( final IPrimitive<?> icon ) {
        this.icon = icon;
    }

    @Override
    public IPrimitive<?> getIcon() {
        return icon;
    }

    @Override
    public HoverAnimation getAnimation() {
        return animation;
    }

    @Override
    public ToolboxButtonEventHandler getClickHandler() {
        return clickHandler;
    }

    @Override
    public ToolboxButtonEventHandler getMouseDownHandler() {
        return mouseDownHandler;
    }

    @Override
    public ToolboxButtonEventHandler getMouseEnterHandler() {
        return mouseEnterHandler;
    }

    @Override
    public ToolboxButtonEventHandler getMouseExitHandler() {
        return mouseExitHandler;
    }

    public LienzoToolboxButton setAnimation( final HoverAnimation animation ) {
        this.animation = animation;
        return this;
    }

    public LienzoToolboxButton setClickHandler( final ToolboxButtonEventHandler clickHandler) {
        this.clickHandler = clickHandler;
        return this;
    }

    public LienzoToolboxButton setMouseDownHandler( final ToolboxButtonEventHandler mouseDownHandler ) {
        this.mouseDownHandler = mouseDownHandler;
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
