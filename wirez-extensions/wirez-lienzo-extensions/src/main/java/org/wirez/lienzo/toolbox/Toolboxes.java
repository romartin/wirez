package org.wirez.lienzo.toolbox;

import com.ait.lienzo.client.core.shape.wires.WiresShape;
import org.wirez.lienzo.toolbox.builder.On;

public class Toolboxes {

    public static On hoverToolBoxFor( final WiresShape shape ) {
        return new HoverToolbox.HoverToolboxBuilder( shape );
    }

    public static On staticToolBoxFor( final WiresShape shape ) {
        return new StaticToolbox.StaticToolboxBuilder( shape );
    }

}
