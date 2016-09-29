package org.kie.workbench.common.stunner.shapes.client.view.icon.dynamics;

import com.ait.lienzo.client.core.shape.MultiPath;
import org.kie.workbench.common.stunner.core.client.shape.view.HasSize;
import org.kie.workbench.common.stunner.shapes.client.view.BasicShapeView;
import org.kie.workbench.common.stunner.shapes.def.icon.dynamics.Icons;

public class DynamicIconShapeView<T extends DynamicIconShapeView> 
        extends BasicShapeView<T>
        implements HasSize<T>  {

    private final static String BLACK = "#000000";
    private final static double STROKE_WIDTH = 0;
    
    private Icons icon;
    private double width;
    private double height;
    
    public DynamicIconShapeView(final Icons icon,
                                final double width,
                                final double height ) {
        super( buildIcon( new MultiPath(),
                icon, 
                width, 
                height,
                BLACK,
                BLACK,
                STROKE_WIDTH ) );
        this.icon = icon;
        this.width = width;
        this.height = height;
    }

    @Override
    protected void doDestroy() {
        super.doDestroy();
        
        icon = null;
        width = 0;
        height = 0;
    }

    public T setIcon( final Icons icon ) {
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
                getStrokeColor(),
                getStrokeWidth() );
        
    }

    private static MultiPath buildIcon(final MultiPath path, 
                                       final Icons icon,
                                       final double w,
                                       final double h,
                                       final String fillColor,
                                       final String strokeColor,
                                       final double strokeWidth) {

        return DynamicIconsBuilder.build( path, icon, w, h )
                .setFillColor( fillColor )
                .setStrokeColor( strokeColor )
                .setStrokeWidth( strokeWidth );
    }

}
