package org.kie.workbench.common.stunner.shapes.client.view.icon.statics;

import com.ait.lienzo.client.core.shape.Group;
import com.ait.lienzo.client.core.shape.MultiPath;
import com.ait.lienzo.client.core.shape.wires.WiresLayoutContainer;
import com.ait.lienzo.client.core.types.BoundingBox;
import org.kie.workbench.common.stunner.shapes.client.view.BasicShapeView;
import org.kie.workbench.common.stunner.shapes.def.icon.statics.Icons;

public class StaticIconShapeView<T extends StaticIconShapeView> 
        extends BasicShapeView<T> {

    private Icons icon;
    private double width;
    private double height;
    private Group iconGroup;
    
    public StaticIconShapeView( final Icons icon ) {
        
        super( new MultiPath()
                .setStrokeWidth( 0 )
                .setStrokeAlpha( 0 ) );
        
        this.setIcon( icon );
        
    }

    public T setIcon( final Icons icon ) {
        this.icon = icon;

        this.buildIconView();

        return (T) this;
    }

    
    private void buildIconView() {
        
        getPath().clear();
        
        if ( null != iconGroup ) {
        
            this.removeChild( iconGroup );
        }
        
        iconGroup = StaticIconsBuilder.build( icon );
        
        if ( null != iconGroup ) {
            
            final BoundingBox bb = iconGroup.getBoundingBox();
            final double w = bb.getWidth();
            final double h = bb.getHeight();
            
            this.width = w;
            this.height = h;
            
            getPath().rect( 0, 0, w, h );
            
            this.addChild( iconGroup, WiresLayoutContainer.Layout.CENTER );


        }

        refresh();
        
    }

    @Override
    protected void doDestroy() {
        super.doDestroy();
        
        if ( null != iconGroup ) {
            
            iconGroup.removeFromParent();
            iconGroup = null;
        }
        
        icon = null;
        
    }

}
