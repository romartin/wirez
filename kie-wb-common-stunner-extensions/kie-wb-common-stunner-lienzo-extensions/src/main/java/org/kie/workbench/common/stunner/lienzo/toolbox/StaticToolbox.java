package org.kie.workbench.common.stunner.lienzo.toolbox;

import com.ait.lienzo.client.core.shape.Shape;
import com.ait.lienzo.client.core.shape.wires.WiresShape;
import com.ait.lienzo.shared.core.types.Direction;
import org.kie.workbench.common.stunner.lienzo.toolbox.builder.AbstractBuilder;
import org.kie.workbench.common.stunner.lienzo.toolbox.grid.GridToolbox;

import java.util.List;

public class StaticToolbox extends AbstractToolbox {

    private StaticToolbox(final WiresShape shape,
                         final Shape<?> attachTo,
                         final Direction anchor,
                         final Direction towards,
                         final int rows,
                         final int cols,
                         final int padding,
                         final int iconSize,
                         final List<ToolboxButton> buttons) {
        super( shape, attachTo, anchor, towards, rows, cols, padding, iconSize, buttons );
    }

    public static class StaticToolboxBuilder extends AbstractBuilder {

        public StaticToolboxBuilder( final WiresShape shape ) {
            super( shape );
        }

        @Override
        public GridToolbox register() {
            return new StaticToolbox( this.shape, this.attachTo, this.anchor, this.towards, this.rows, this.cols,
                    this.padding, this.iconSize, this.buttons );
        }

    }

}
