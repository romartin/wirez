package org.wirez.shapes.client.view;

import com.ait.lienzo.client.core.shape.MultiPath;
import org.wirez.core.client.shape.view.HasRadius;

public class CircleView extends BasicShapeView<CircleView>
        implements HasRadius<CircleView> {

    public CircleView( final double radius ) {
        super( create( new MultiPath(), radius ) );
    }

    @Override
    public CircleView setRadius( final double radius ) {

        create( getPath().clear(), radius );

        updateFillGradient( radius * 2, radius * 2 );

        refresh();

        return this;

    }

    private static MultiPath create( final MultiPath path,
                                     final double radius ) {
        return path.M( 0, -radius ).circle( radius );
    }

}
