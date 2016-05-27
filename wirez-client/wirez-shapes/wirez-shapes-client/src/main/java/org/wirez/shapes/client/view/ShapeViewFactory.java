package org.wirez.shapes.client.view;

import com.ait.lienzo.client.core.shape.wires.WiresManager;
import org.wirez.shapes.client.view.icon.dynamics.DynamicIconShapeView;
import org.wirez.shapes.client.view.icon.statics.StaticIconShapeView;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ShapeViewFactory {

    public RectangleView rectangle( final double width,
                                    final double height,
                                    final WiresManager wiresManager ) {
        
        return new RectangleView( width, height, wiresManager );
        
    }

    public DynamicIconShapeView dynamicIcon( final org.wirez.shapes.proxy.icon.dynamics.Icons icon,
                                             final double width,
                                             final double height,
                                             final WiresManager manager ) {
        
        return new DynamicIconShapeView( icon, width, height, manager );
        
    }

    public StaticIconShapeView staticIcon( final org.wirez.shapes.proxy.icon.statics.Icons icon,
                                           final WiresManager manager ) {
        
        return new StaticIconShapeView( icon, manager );
        
    }

    public CircleView circle( final double radius,
                              final WiresManager wiresManager ) {
        
        return new CircleView( radius, wiresManager );
        
    }

    public RingView ring( final double outer,
                          final WiresManager wiresManager ) {
        
        return new RingView( outer, wiresManager );
        
    }

    public PolygonView polygon( final double radius,
                                final String fillColor,
                                final WiresManager wiresManager ) {
        
        return new PolygonView( radius, fillColor, wiresManager );
        
    }

    public ConnectorView connector( final WiresManager manager,
                                    final double... points ) {
        
        return new ConnectorView( manager, points );
        
    }

}
