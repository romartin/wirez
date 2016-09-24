package org.wirez.shapes.client.view;

import com.ait.lienzo.client.core.shape.MultiPath;
import org.wirez.core.client.shape.view.HasSize;

public class RectangleView extends BasicShapeView<RectangleView>
        implements HasSize<RectangleView> {

    public RectangleView( final double width,
                          final double height ) {

        super( create( new MultiPath(), width, height ) );
    }

    @Override
    public RectangleView setSize( final double width,
                      final double height ) {

        create( getPath().clear(), width, height );

        updateFillGradient( width, height );

        refresh();

        return this;

    }

    private static MultiPath create( final MultiPath path,
                                     final double width,
                                     final double height ) {
        return path.rect( 0, 0, width, height );
    }
}
