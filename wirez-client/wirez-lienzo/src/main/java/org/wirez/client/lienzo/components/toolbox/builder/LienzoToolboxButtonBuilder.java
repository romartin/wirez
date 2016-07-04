package org.wirez.client.lienzo.components.toolbox.builder;

import com.ait.lienzo.client.core.shape.IPrimitive;
import com.ait.lienzo.client.core.shape.IPrimitive;
import org.wirez.client.lienzo.components.toolbox.LienzoToolboxButton;
import org.wirez.core.client.components.toolbox.ToolboxButton;
import org.wirez.core.client.components.toolbox.builder.ToolboxButtonBuilder;
import org.wirez.core.client.components.toolbox.event.ToolboxButtonEventHandler;

public class LienzoToolboxButtonBuilder implements ToolboxButtonBuilder<IPrimitive<?>> {

    private IPrimitive<?> icon;
    private ToolboxButtonEventHandler clickHandler;
    private ToolboxButtonEventHandler dragEndHandler;
    private ToolboxButtonEventHandler mouseEnterHandler;
    private ToolboxButtonEventHandler mouseExitHandler;

    @Override
    public ToolboxButtonBuilder<IPrimitive<?>> setIcon( final IPrimitive<?> icon ) {
        this.icon = icon;
        return this;
    }

    @Override
    public ToolboxButtonBuilder<IPrimitive<?>> setClickHandler( final ToolboxButtonEventHandler handler ) {
        this.clickHandler = handler;
        return this;
    }

    @Override
    public ToolboxButtonBuilder<IPrimitive<?>> setDragEndHandler( final ToolboxButtonEventHandler handler ) {
        this.dragEndHandler = handler;
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
                .setClickHandler( clickHandler )
                .setDragHandler( dragEndHandler )
                .setMouseEnterHandler( mouseEnterHandler )
                .setMouseExitHandler( mouseExitHandler );
    }
    
}
