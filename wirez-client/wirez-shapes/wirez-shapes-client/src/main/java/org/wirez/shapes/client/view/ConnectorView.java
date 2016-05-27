package org.wirez.shapes.client.view;

import com.ait.lienzo.client.core.shape.AbstractDirectionalMultiPointShape;
import com.ait.lienzo.client.core.shape.MultiPath;
import com.ait.lienzo.client.core.shape.MultiPathDecorator;
import com.ait.lienzo.client.core.shape.OrthogonalPolyLine;
import com.ait.lienzo.client.core.shape.wires.WiresManager;
import com.ait.lienzo.client.core.types.Point2DArray;
import com.google.gwt.event.shared.HandlerRegistration;
import org.wirez.core.client.shape.view.event.ViewEvent;
import org.wirez.core.client.shape.view.event.ViewEventType;
import org.wirez.core.client.shape.view.event.ViewHandler;

public class ConnectorView<T extends ConnectorView> extends org.wirez.shapes.client.view.animatiion.AnimatedWiresConnectorView<T> {
    
    public ConnectorView(final WiresManager manager,
                         final double... points) {
        this( manager, createLine( points ) );
    }
    
    private ConnectorView(final  WiresManager wiresManager,
                         final Object[] line) {
        super( (OrthogonalPolyLine) line[0],
                (MultiPathDecorator) line[1],
                (MultiPathDecorator) line[2],
                wiresManager );
    }

    @Override
    protected HandlerRegistration doAddHandler(final ViewEventType type, final ViewHandler<ViewEvent> eventHandler) {
        if ( ViewEventType.MOUSE_CLICK.equals( type ) ) {
            return registerClickHandler(getLine(), eventHandler);
        }
        
        return null;
    }

    @Override
    public boolean supports(final ViewEventType type) {
        return ViewEventType.MOUSE_CLICK.equals( type );
    }

    private static Object[] createLine(final double... points) {
        
        final  MultiPath head = new MultiPath()
                .M( 1, 2 )
                .L( 0, 2 )
                .L( 1 / 2, 0 )
                .Z()
                // TODO: Remove when decorators can be nullified for the WiresConnector
                .setFillAlpha( 0 )
                .setStrokeAlpha( 0 );
        
        final MultiPath tail =  new MultiPath()
                .M( 15, 20 )
                .L( 0, 20 )
                .L( 15 / 2, 0 )
                .Z();
        
        final OrthogonalPolyLine line =  new OrthogonalPolyLine(Point2DArray.fromArrayOfDouble(points)).setCornerRadius(5).setDraggable(true);
        line.setHeadOffset(head.getBoundingBox().getHeight());
        line.setTailOffset(tail.getBoundingBox().getHeight());
        
        final MultiPathDecorator headDecorator = new MultiPathDecorator( head );
        final MultiPathDecorator tailDecorator = new MultiPathDecorator( tail );
        
        return new Object[] { line, headDecorator, tailDecorator };
    }
    
}
