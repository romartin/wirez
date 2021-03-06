package org.kie.workbench.common.stunner.shapes.client.view;

import com.ait.lienzo.client.core.shape.MultiPath;
import com.ait.lienzo.client.core.shape.MultiPathDecorator;
import com.ait.lienzo.client.core.shape.OrthogonalPolyLine;
import com.ait.lienzo.client.core.types.Point2DArray;

public class ConnectorView extends BasicConnectorView<ConnectorView> {
    
    public ConnectorView(final double... points) {
        this( createLine( points ) );
    }
    
    private ConnectorView(final Object[] line) {
        super( (OrthogonalPolyLine) line[0],
                (MultiPathDecorator) line[1],
                (MultiPathDecorator) line[2] );
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
