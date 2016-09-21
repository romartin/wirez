package org.wirez.shapes.client.view;

import org.wirez.shapes.client.view.icon.dynamics.DynamicIconShapeView;
import org.wirez.shapes.client.view.icon.statics.StaticIconShapeView;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ShapeViewFactory {

    public RectangleView rectangle( final double width,
                                    final double height ) {
        
        return new RectangleView( width, height );
        
    }

    public DynamicIconShapeView dynamicIcon( final org.wirez.shapes.proxy.icon.dynamics.Icons icon,
                                             final double width,
                                             final double height ) {
        
        return new DynamicIconShapeView( icon, width, height );
        
    }

    public StaticIconShapeView staticIcon( final org.wirez.shapes.proxy.icon.statics.Icons icon ) {
        
        return new StaticIconShapeView( icon );
        
    }

    public CircleView circle( final double radius ) {
        
        return new CircleView( radius );
        
    }

    public RingView ring( final double outer ) {
        
        return new RingView( outer );
        
    }

    public PolygonView polygon( final double radius,
                                final String fillColor ) {
        
        return new PolygonView( radius, fillColor );
        
    }

    public ConnectorView connector( final double... points ) {
        
        return new ConnectorView( points );
        
    }

}
