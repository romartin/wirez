package org.wirez.core.client.util;

import com.ait.lienzo.client.core.types.LinearGradient;
import org.wirez.core.client.Shape;
import org.wirez.core.client.ShapeSet;

import java.util.Collection;

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
    
    public static ShapeSet getShapeSet(final Collection<ShapeSet> shapeSets, final String id) {
        if ( null != id && null != shapeSets ) {
            
            for (final ShapeSet shapeSet : shapeSets) {
                if (id.equals(shapeSet.getId())) {
                    return shapeSet;
                }
            }
            
        }
        
        return null;
    }

    public static double[] getContainerXY(final Shape shape) {
        return new double[] { shape.getShapeContainer().getAttributes().getX(),
                shape.getShapeContainer().getAttributes().getY()};
    }
    
    
}
