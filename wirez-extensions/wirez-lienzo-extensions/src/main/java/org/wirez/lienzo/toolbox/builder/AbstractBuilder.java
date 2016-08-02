package org.wirez.lienzo.toolbox.builder;

import com.ait.lienzo.client.core.shape.IPrimitive;
import com.ait.lienzo.client.core.shape.Layer;
import com.ait.lienzo.client.core.shape.Shape;
import com.ait.lienzo.client.core.shape.wires.WiresShape;
import com.ait.lienzo.shared.core.types.Direction;
import org.wirez.lienzo.toolbox.HoverToolbox;
import org.wirez.lienzo.toolbox.ToolboxButton;
import org.wirez.lienzo.toolbox.event.ToolboxButtonEventHandler;
import org.wirez.lienzo.toolbox.grid.GridToolbox;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractBuilder implements On, Towards, ButtonsOrRegister, ButtonGrid {

    protected WiresShape shape;
    protected Shape<?> attachTo;
    protected Direction anchor;
    protected Direction towards;
    protected List<ToolboxButton> buttons = new ArrayList<>();
    protected int cols;
    protected int rows;
    protected int padding;
    protected int iconSize;

    public AbstractBuilder(WiresShape shape) {
        this.shape = shape;
    }

    @Override
    public Towards on(Direction anchor) {
        this.anchor = anchor;
        return this;
    }

    @Override
    public On attachTo(Shape<?> shape) {
        this.attachTo = shape;
        return this;
    }

    @Override
    public ButtonGrid towards(Direction towards) {
        this.towards = towards;
        return this;
    }

    @Override
    public ButtonsOrRegister add(ToolboxButton button) {
        this.buttons.add(button);
        return this;
    }

    @Override
    public ButtonsOrRegister grid(int padding, int iconSize, int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.padding = padding;
        this.iconSize = iconSize;
        return this;
    }

    @Override
    public Button add(IPrimitive<?> iconShape) {
        return new ButtonBuilder(this.shape.getWiresLayer().getLayer(), this, iconShape);
    }

    @Override
    public abstract GridToolbox register();

    public static class ButtonBuilder implements Button {
        private final Layer layer;
        private final AbstractBuilder builder;
        private final List<WhenReady> whenReadyCallbacks = new ArrayList<>();
        private final IPrimitive<?> shape;
        private ToolboxButtonEventHandler clickHandler;
        private ToolboxButtonEventHandler moveDownHandler;
        private ToolboxButtonEventHandler mouseEnterHandler;
        private ToolboxButtonEventHandler mouseExitHandler;
        private ToolboxButton.HoverAnimation animation;

        public ButtonBuilder( final Layer layer,
                              final AbstractBuilder builder,
                              final IPrimitive<?> shape) {
            this.layer = layer;
            this.builder = builder;
            this.shape = shape;
            this.animation = ToolboxButton.HoverAnimation.ELASTIC;
        }

        @Override
        public Button setAnimation( final ToolboxButton.HoverAnimation animation ) {
            this.animation = animation;
            return this;
        }

        @Override
        public Button setClickHandler(final ToolboxButtonEventHandler handler ) {
            this.clickHandler = handler;
            return this;
        }

        @Override
        public Button setMouseDownHandler(final ToolboxButtonEventHandler handler ) {
            this.moveDownHandler = handler;
            return this;
        }

        @Override
        public Button setMouseEnterHandler(final ToolboxButtonEventHandler handler ) {
            this.mouseEnterHandler = handler;
            return this;
        }

        @Override
        public Button setMouseExitHandler(final ToolboxButtonEventHandler handler ) {
            this.mouseExitHandler = handler;
            return this;
        }
        
        @Override
        public Button whenReady(WhenReady callback) {
            this.whenReadyCallbacks.add(callback);
            return this;
        }

        @Override
        public ButtonsOrRegister end() {
            builder.add( new ToolboxButton( layer, shape, this.whenReadyCallbacks, clickHandler, moveDownHandler,
                    mouseEnterHandler, mouseExitHandler, animation ) );
            return builder;
        }
    }

}
