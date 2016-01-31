package org.wirez.core.client.util;

import com.ait.lienzo.client.core.types.LinearGradient;

public class ShapeUtils {
    
    public static LinearGradient getLinearGradient(final String startColor,
                                                   final String endColor, 
                                                   final Double width, 
                                                   final Double height) {

        final LinearGradient linearGradient = new LinearGradient(0, width, 0, - height );
        linearGradient.addColorStop(1, endColor );
        linearGradient.addColorStop(0, startColor );
        
        return linearGradient;
    }
}
