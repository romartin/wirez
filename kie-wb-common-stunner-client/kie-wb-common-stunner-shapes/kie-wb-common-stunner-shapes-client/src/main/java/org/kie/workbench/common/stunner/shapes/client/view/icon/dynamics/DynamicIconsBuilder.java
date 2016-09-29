package org.kie.workbench.common.stunner.shapes.client.view.icon.dynamics;

import com.ait.lienzo.client.core.shape.MultiPath;
import org.kie.workbench.common.stunner.shapes.proxy.icon.dynamics.Icons;

public class DynamicIconsBuilder {

    public static MultiPath build( final MultiPath path,
                                   final Icons icon,
                                   final double w,
                                   final double h ) {
        
        return build( path, icon, 0, 0, w, h );
    }

    public static MultiPath build( final MultiPath path,
                                   final Icons icon,
                                   final double x,
                                   final double y,
                                   final double w,
                                   final double h ) {

        path.clear();

        switch (icon) {

            case PLUS:

                path
                        .M(x, y + (h / 2))
                        .L(x + w, y + (h / 2))
                        .M(x + (w / 2), y)
                        .L(x + (w / 2), y + h);

                break;

            case MINUS:

                path
                        .M( x, y + ( h / 2) )
                        .L( x + w, y + ( h / 2) );

                break;

            case XOR:

                path
                        .M( x, y )
                        .L( x + w, y + h )
                        .M( x + w, y )
                        .L( x, y + h );

                break;

        }


        return path
                .Z();

    }
    
}
