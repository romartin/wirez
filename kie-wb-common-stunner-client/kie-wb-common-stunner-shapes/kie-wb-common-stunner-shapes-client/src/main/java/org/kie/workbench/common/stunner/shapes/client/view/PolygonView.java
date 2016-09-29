package org.kie.workbench.common.stunner.shapes.client.view;

import com.ait.lienzo.client.core.shape.MultiPath;
import com.ait.lienzo.client.core.types.Point2DArray;
import com.ait.lienzo.client.core.util.Geometry;
import org.kie.workbench.common.stunner.core.client.shape.view.HasRadius;

public class PolygonView extends BasicShapeView<PolygonView>
        implements HasRadius<PolygonView> {

    private static final int SIDES = 4;
    private static final double CORNER= 0;

    public PolygonView(final double radius,
                       final String fillColor ) {
        super( create( new MultiPath(), SIDES, radius, CORNER ) );
    }

    @Override
    public PolygonView setRadius(final double radius) {
        create( getPath().clear(), SIDES, radius, CORNER );
        return this;
    }

    // TODO: If cornerRadius > 0 -> bug.
    private static MultiPath create( final MultiPath result,
                                     final int sides,
                                     final double radius,
                                     final double cornerRadius ) {

        final double ix = radius;
        final double iy = radius;

        if ((sides > 2) && (radius > 0))
        {
            result.M( ix, iy - radius);

            if (cornerRadius <= 0)
            {
                for (int n = 1; n < sides; n++)
                {
                    final double theta = (n * 2 * Math.PI / sides);

                    result.L( ix + ( radius * Math.sin(theta) ), iy + ( -1 * radius * Math.cos(theta) ) );
                }
                result.Z();
            }
            else
            {
                final Point2DArray list = new Point2DArray( ix, iy - radius);

                for (int n = 1; n < sides; n++)
                {
                    final double theta = (n * 2 * Math.PI / sides);

                    list.push( ix + ( radius * Math.sin(theta) ), iy + ( -1 * radius * Math.cos(theta) ) );
                }
                Geometry.drawArcJoinedLines( result.getPathPartList(), list.push( ix, iy - radius ), cornerRadius );
            }
        }

        return result;
    }

}
