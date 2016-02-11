package org.wirez.core.client.util;

import com.ait.lienzo.client.core.shape.IPrimitive;
import com.ait.lienzo.client.core.shape.wires.MagnetManager;
import com.ait.lienzo.client.core.types.LinearGradient;
import com.google.gwt.core.client.GWT;
import org.wirez.core.api.graph.Bounds;
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.content.ViewContent;
import org.wirez.core.api.util.ElementUtils;
import org.wirez.core.client.Shape;
import org.wirez.core.client.ShapeSet;
import org.wirez.core.client.impl.BaseShape;

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


    /**
     * Obtain the magnet indexes on the source and target shapes when connecting 
     * them based. The resulting index values are on the min distance between the magnets, which is calculated 
     * by basic trigonometry on a cartesian coordinates graph.  
     */
    public static int[] getDefaultMagnetsIndex(final BaseShape sourceShape, final BaseShape targetShape) {
        final MagnetManager.Magnets sourceMagnets = sourceShape.getMagnets();
        final MagnetManager.Magnets targetMagnets = targetShape.getMagnets();
        
        int sMagnet = 0;
        int tMagnet = 0;
        double dist = Double.MAX_VALUE;
        for (int x = 0; x < sourceMagnets.size(); x++) {
            final IPrimitive<?> sourceControl = sourceMagnets.getMagnet(x).getControl();
            final double sX = sourceControl.getX();
            final double sY = sourceControl.getY();
            for (int y = 0; y < targetMagnets.size(); y++) {
                final IPrimitive<?> targetControl = targetMagnets.getMagnet(y).getControl();
                final double tX = targetControl.getX();
                final double tY = targetControl.getY();
                final double _d = dist(sX, sY, tX, tY);
                GWT.log("Dist=" + _d + " / s=" + x + ", t=" + y);
                if ( _d < dist ) {
                    dist = _d;
                    sMagnet = x;
                    tMagnet = y;
                }
                
            }
        }
        
        return new int[] { sMagnet, tMagnet };
    }

    /**
     * Returns the distance between two points in a dual axis cartesian graph. 
     */
    public static double dist(final double x0, final double y0, final double x1, final double y1) {
        final double dx = Math.abs( x1 - x0 );
        final double dy = Math.abs( y1 - y0 );
        return ( Math.sqrt( ( dx * dx ) + ( dy * dy ) ) );
    }
}
