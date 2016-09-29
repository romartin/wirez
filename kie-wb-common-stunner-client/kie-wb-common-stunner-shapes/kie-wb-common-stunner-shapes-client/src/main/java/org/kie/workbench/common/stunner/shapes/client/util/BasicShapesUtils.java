package org.kie.workbench.common.stunner.shapes.client.util;

import com.ait.lienzo.client.core.shape.wires.WiresLayoutContainer;
import org.kie.workbench.common.stunner.core.client.shape.HasChildren;

public class BasicShapesUtils {

    public static WiresLayoutContainer.Layout getWiresLayout(final HasChildren.Layout layout ) {

        switch ( layout ) {

            case CENTER:
                return WiresLayoutContainer.Layout.CENTER;

            case LEFT:
                return WiresLayoutContainer.Layout.LEFT;

            case RIGHT:
                return WiresLayoutContainer.Layout.RIGHT;

            case TOP:
                return WiresLayoutContainer.Layout.TOP;

            case BOTTOM:
                return WiresLayoutContainer.Layout.BOTTOM;

        }

        throw new UnsupportedOperationException( "Unsupported layout [" + layout.name() + "]" );
    }
    
}
