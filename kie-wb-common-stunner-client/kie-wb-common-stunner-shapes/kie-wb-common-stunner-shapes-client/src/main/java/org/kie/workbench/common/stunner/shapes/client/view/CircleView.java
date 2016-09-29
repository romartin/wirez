package org.kie.workbench.common.stunner.shapes.client.view;

import com.ait.lienzo.client.core.shape.MultiPath;
import org.kie.workbench.common.stunner.core.client.shape.view.HasRadius;

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
        return path.M( radius, 0 ).circle( radius );
    }

}
