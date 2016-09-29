package org.wirez.shapes.client.view;

import com.ait.lienzo.client.core.shape.MultiPath;
import com.ait.lienzo.client.core.shape.Ring;
import com.ait.lienzo.client.core.shape.Shape;
import com.ait.lienzo.client.core.shape.wires.LayoutContainer;
import com.ait.lienzo.client.core.shape.wires.event.*;
import org.wirez.core.client.shape.view.HasRadius;
import org.wirez.core.client.shape.view.event.HandlerRegistrationImpl;

public class RingView extends BasicShapeView<RingView>
    implements HasRadius<RingView> {

    private static final int INNER_RADIUS_FACTOR = 2;

    private Ring ring;
    private final HandlerRegistrationImpl registrations = new HandlerRegistrationImpl();

    public RingView( final double radius ) {
        super( initPath( new MultiPath(), radius ) );
        ring = new Ring( getInnerRadius( radius ), getOuterRadius( radius ) );
        addChild( ring, LayoutContainer.Layout.CENTER );
        initResizeHandlers();
    }

    @Override
    public Shape<?> getShape() {
        return ring;
    }

    @Override
    public RingView setRadius( final double radius ) {

        initPath( getPath().clear(), radius );

        updateFillGradient( radius * 2, radius * 2);

        final double o = getOuterRadius( radius );
        final double i = getInnerRadius( radius );
        ring.setOuterRadius( o );
        ring.setInnerRadius( i );

        refresh();

        return this;
    }

    @SuppressWarnings("unchecked")
    public RingView setOuterRadius(final double radius ) {

        return setRadius( radius );

    }

    @SuppressWarnings("unchecked")
    public RingView setInnerRadius(final double inner) {

        return setOuterRadius( inner * INNER_RADIUS_FACTOR  );

    }

    @Override
    protected void doDestroy() {
        super.doDestroy();
        registrations.removeHandler();
        ring.removeFromParent();

    }

    private void initResizeHandlers() {
        registrations.register(
                this.addWiresResizeStartHandler( new WiresResizeStartHandler() {
                    @Override
                    public void onShapeResizeStart( WiresResizeStartEvent wiresResizeStartEvent ) {
                        resize( wiresResizeStartEvent.getWidth(), wiresResizeStartEvent.getHeight() );
                    }
                } )
        );

        registrations.register(
                this.addWiresResizeStepHandler( new WiresResizeStepHandler() {
                    @Override
                    public void onShapeResizeStep( WiresResizeStepEvent wiresResizeStepEvent ) {
                        resize( wiresResizeStepEvent.getWidth(), wiresResizeStepEvent.getHeight() );
                    }
                } )
        );

        registrations.register(
                this.addWiresResizeEndHandler( new WiresResizeEndHandler() {
                    @Override
                    public void onShapeResizeEnd( WiresResizeEndEvent wiresResizeEndEvent ) {
                        resize( wiresResizeEndEvent.getWidth(), wiresResizeEndEvent.getHeight() );
                    }
                } )
        );
    }

    private void resize( final double width, final double height ) {
        this.setRadius( width >= height ? height : width );
    }

    private static MultiPath initPath( final MultiPath path, final double radius ) {
        return path.rect( 0, 0, radius * 2, radius * 2 )
                .setStrokeWidth( 0 )
                .setStrokeAlpha( 0 );
    }

    private static double getOuterRadius( final double radius ) {
        return radius;
    }

    private static double getInnerRadius( final double radius ) {
        return radius / INNER_RADIUS_FACTOR;
    }

}
