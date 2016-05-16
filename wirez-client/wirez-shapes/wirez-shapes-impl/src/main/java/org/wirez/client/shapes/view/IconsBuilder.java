package org.wirez.client.shapes.view;

import com.ait.lienzo.client.core.shape.MultiPath;
import org.wirez.client.shapes.proxy.icon.ICONS;

public class IconsBuilder {

    public static MultiPath build(final MultiPath path,
                                   final ICONS icon,
                                   final double w,
                                   final double h,
                                   final String fillColor,
                                   final double strokeWidth) {
        
        return build( path, icon, 0, 0, w, h, fillColor, strokeWidth );
    }

    public static MultiPath build(final MultiPath path,
                                  final ICONS icon,
                                  final double x,
                                  final double y,
                                  final double w,
                                  final double h,
                                  final String fillColor,
                                  final double strokeWidth) {

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
                .Z()
                .setStrokeColor( fillColor )
                .setStrokeWidth( strokeWidth );

    }
    
}
