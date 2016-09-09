package org.wirez.shapes.client.view;

import com.ait.lienzo.client.core.shape.MultiPath;
import org.wirez.core.client.shape.view.HasSize;

public class RectangleView extends BasicShapeView<RectangleView>
        implements HasSize<RectangleView> {

    // TODO: Arc resize on lienzo is not supported. Cannot use corner radius.
    private static final double CORNER_RADIUS = 0;

    public RectangleView( final double width,
                          final double height ) {

        super( create( new MultiPath(), width, height, CORNER_RADIUS ) );
    }

    @Override
    public RectangleView setSize( final double width,
                                  final double height ) {

        create( getPath().clear(), width, height, CORNER_RADIUS );

        updateFillGradient( width, height );

        refresh();

        return this;

    }

    /**
     * Append the path parts for a rectangle.
     * @param path The source multipath
     * @param w The rectangle width
     * @param h The rectangle height
     * @param r The rectangle corner radius
     */
    private static MultiPath create( final MultiPath path,
                                     final double w,
                                     final double h,
                                     final double r ) {

        if ((w > 0) && (h > 0)) {
            if ((r > 0) && (r < (w / 2)) && (r < (h / 2))) {

                path.M(r, 0);

                path.L(w - r, 0);

                path.A( w , 0, w, r, r );

                path.L(w, h - r);

                path.A( w, h, w - r, h, r );

                path.L(r, h);

                path.A( 0, h, 0, h - r , r );

                path.L(0, r);

                path.A( 0, 0, r, 0, r );


            } else {

                path.rect(0, 0, w, h);

            }

            path.Z();

        }

        return path;

    }
}
