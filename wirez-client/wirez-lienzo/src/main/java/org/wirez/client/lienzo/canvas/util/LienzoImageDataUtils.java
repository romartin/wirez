package org.wirez.client.lienzo.canvas.util;

import com.ait.lienzo.client.core.shape.Layer;
import com.ait.lienzo.client.core.types.ImageData;
import com.ait.lienzo.client.widget.LienzoPanel;

public class LienzoImageDataUtils {

    public static String toImageData( final Layer layer,
                                      final int x,
                                      final int y,
                                      final int width,
                                      final int height ) {

        final ImageData imageData = layer.getContext().getImageData( x, y, width, height );

        if ( null != imageData ) {

            final LienzoPanel p = new LienzoPanel( width, height );
            final Layer l = new Layer().setTransformable( true );
            p.add( l );

            l.getContext().putImageData( imageData, 0, 0 );

            return l.toDataURL();

        }

        return null;

    }


}
