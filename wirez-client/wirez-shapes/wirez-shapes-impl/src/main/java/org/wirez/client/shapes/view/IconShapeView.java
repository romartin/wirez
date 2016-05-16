package org.wirez.client.shapes.view;

import com.ait.lienzo.client.core.shape.MultiPath;
import com.ait.lienzo.client.core.shape.Rectangle;
import com.ait.lienzo.client.core.shape.wires.WiresLayoutContainer;
import com.ait.lienzo.client.core.shape.wires.WiresManager;
import org.wirez.client.shapes.proxy.icon.ICONS;
import org.wirez.core.client.shape.view.HasSize;

public class IconShapeView<T extends IconShapeView> 
        extends AbstractDecoratableShapeView<T>
        implements HasSize<T>  {

    private final static String BLACK = "#000000";
    private final static double STROKE_WIDTH = 0;
    
    private ICONS icon;
    private double width;
    private double height;
    
    public IconShapeView(final ICONS icon,
                         final double width,
                         final double height,
                         final WiresManager manager ) {
        super( buildIcon( new MultiPath(),
                icon, 
                width, 
                height,
                BLACK,
                STROKE_WIDTH ), 
                manager );
        this.icon = icon;
        this.width = width;
        this.height = height;
    }

    @Override
    protected Rectangle createDecorator() {
        final Rectangle r = new Rectangle( width, height );
        this.addChild( r, WiresLayoutContainer.Layout.CENTER );
        return r;
    }

    public T setIcon( final ICONS icon ) {
        this.icon = icon;
        updateIcon();
        
        return (T) this;
    }

    @Override
    public T setSize( final double width, 
                      final double height) {

        this.width = width;
        this.height = height;
        
        updateIcon();
        
        return (T) this;
    }

    private void updateIcon() {

        buildIcon( getPath(), 
                icon, 
                width, 
                height, 
                getFillColor(), 
                getStrokeWidth() );
        
        // Force to redraw the decorator.
        super.createDecorators();

        this.moveChild( decorator, - ( width / 2) , - ( height / 2 ) );

    }

    private static MultiPath buildIcon(final MultiPath path, 
                                       final ICONS icon,
                                       final double w,
                                       final double h,
                                       final String fillColor,
                                       final double strokeWidth) {

        return IconsBuilder.build( path, icon, w, h, fillColor, strokeWidth );
    }

}
