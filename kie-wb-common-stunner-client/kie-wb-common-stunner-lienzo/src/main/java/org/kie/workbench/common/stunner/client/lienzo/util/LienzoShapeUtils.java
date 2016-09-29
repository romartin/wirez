package org.kie.workbench.common.stunner.client.lienzo.util;

import com.ait.lienzo.client.core.shape.IPrimitive;
import com.ait.lienzo.client.core.shape.wires.MagnetManager;
import com.ait.lienzo.client.core.shape.wires.WiresShape;
import com.ait.lienzo.client.core.types.LinearGradient;
import org.kie.workbench.common.stunner.core.client.shape.Shape;
import org.kie.workbench.common.stunner.core.client.shape.view.ShapeView;
import org.kie.workbench.common.stunner.core.client.util.ShapeUtils;

public class LienzoShapeUtils {

    public static LinearGradient getLinearGradient(final String startColor,
                                                   final String endColor,
                                                   final Double width,
                                                   final Double height) {

        final LinearGradient linearGradient = new LinearGradient(0, width, 0, - height / 2 );
        linearGradient.addColorStop(1, endColor );
        linearGradient.addColorStop(0, startColor );

        return linearGradient;
    }

    /**
     * Obtain the magnet indexes on the source and target shapes when connecting 
     * them based. The resulting index values are on the min distance between the magnets, which is calculated 
     * by basic trigonometry on a cartesian coordinates graph.  
     */
    public static int[] getDefaultMagnetsIndex(final WiresShape sourceShape, final WiresShape targetShape) {
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
                final double _d = ShapeUtils.dist(sX, sY, tX, tY);
                if ( _d < dist ) {
                    dist = _d;
                    sMagnet = x;
                    tMagnet = y;
                }

            }
        }

        return new int[] { sMagnet, tMagnet };
    }

    public static int[] getDefaultMagnetsIndex(final Shape sourceShape, final Shape targetShape) {

        final ShapeView<?> sourceView = sourceShape.getShapeView();
        final ShapeView<?> targetView = targetShape.getShapeView();

        if ( sourceView instanceof WiresShape && targetView instanceof WiresShape ) {

            return getDefaultMagnetsIndex( (WiresShape) sourceView, (WiresShape) targetView );

        }

        return new int[] { 0, 0 };
    }
    
}
