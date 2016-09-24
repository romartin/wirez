package org.wirez.shapes.client.view;

import com.ait.lienzo.client.core.shape.MultiPath;
import org.wirez.core.client.shape.view.HasRadius;

public class RingView extends BasicShapeView<RingView>
    implements HasRadius<RingView> {

    public RingView( final double outer ) {
        super( create( new MultiPath(), outer ) );
    }

    @Override
    public RingView setRadius( final double radius ) {
        final double o = radius;
        final double i = getInnerRadius( radius );
        setOuterRadius( o );
        setInnerRadius( i );
        return this;
    }

    @SuppressWarnings("unchecked")
    public RingView setOuterRadius(final double radius) {

        create( getPath().clear(), radius );
        
        super.updateFillGradient( radius * 2, radius * 2 );
        
        return this;
        
    }

    @SuppressWarnings("unchecked")
    public RingView setInnerRadius(final double radius) {

        // TODO

        return this;

    }

    private static MultiPath create( final MultiPath path,
                                     final double outer ) {
        return create( path, outer, getInnerRadius( outer ) );
    }

    // TODO: Create the inner circle as well.
    private static MultiPath create( final MultiPath path,
                                     final double outer,
                                     final double inner ) {
        return path.circle( outer );
    }

    private static double getInnerRadius( final double outer ) {
        return outer - ( outer / 4 );
    }


}
