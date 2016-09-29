package org.kie.workbench.common.stunner.client.lienzo.components.toolbox.builder;

import com.ait.lienzo.client.core.shape.IPrimitive;
import org.kie.workbench.common.stunner.client.lienzo.components.toolbox.LienzoToolboxButton;
import org.kie.workbench.common.stunner.core.client.components.toolbox.ToolboxButton;
import org.kie.workbench.common.stunner.core.client.components.toolbox.builder.ToolboxButtonBuilder;
import org.kie.workbench.common.stunner.core.client.components.toolbox.event.ToolboxButtonEventHandler;

public class LienzoToolboxButtonBuilder implements ToolboxButtonBuilder<IPrimitive<?>> {

    private IPrimitive<?> icon;
    private ToolboxButton.HoverAnimation animation;
    private ToolboxButtonEventHandler clickHandler;
    private ToolboxButtonEventHandler mouseDownHandler;
    private ToolboxButtonEventHandler mouseEnterHandler;
    private ToolboxButtonEventHandler mouseExitHandler;

    @Override
    public ToolboxButtonBuilder<IPrimitive<?>> setIcon( final IPrimitive<?> icon ) {
        this.icon = icon;
        return this;
    }

    @Override
    public ToolboxButtonBuilder<IPrimitive<?>> setHoverAnimation( ToolboxButton.HoverAnimation animation ) {
        this.animation = animation;
        return this;
    }

    @Override
    public ToolboxButtonBuilder<IPrimitive<?>> setClickHandler( final ToolboxButtonEventHandler handler ) {
        this.clickHandler = handler;
        return this;
    }

    @Override
    public ToolboxButtonBuilder<IPrimitive<?>> setMouseDownHandler( final ToolboxButtonEventHandler handler ) {
        this.mouseDownHandler = handler;
        return this;
    }

    @Override
    public ToolboxButtonBuilder<IPrimitive<?>> setMouseEnterHandler( final ToolboxButtonEventHandler handler ) {
        this.mouseEnterHandler = handler;
        return this;
    }

    @Override
    public ToolboxButtonBuilder<IPrimitive<?>> setMouseExitHandler( final ToolboxButtonEventHandler handler ) {
        this.mouseExitHandler = handler;
        return this;
    }

    @Override
    public ToolboxButton<IPrimitive<?>> build() {
        return new LienzoToolboxButton( icon )
                .setAnimation( animation )
                .setClickHandler( clickHandler )
                .setMouseDownHandler( mouseDownHandler )
                .setMouseEnterHandler( mouseEnterHandler )
                .setMouseExitHandler( mouseExitHandler );
    }
    
}
