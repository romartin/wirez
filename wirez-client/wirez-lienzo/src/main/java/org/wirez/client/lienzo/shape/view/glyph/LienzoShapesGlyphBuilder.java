package org.wirez.client.lienzo.shape.view.glyph;

import com.ait.lienzo.client.core.shape.Group;
import com.ait.lienzo.client.core.shape.IPrimitive;
import org.wirez.client.lienzo.shape.view.AbstractConnectorView;
import org.wirez.client.lienzo.shape.view.AbstractShapeView;
import org.wirez.core.api.FactoryManager;
import org.wirez.core.client.shape.Shape;
import org.wirez.core.client.shape.view.*;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class LienzoShapesGlyphBuilder extends AbstractShapeGlyphBuilder<Group> {

    FactoryManager factoryManager;

    @Inject
    public LienzoShapesGlyphBuilder(final FactoryManager factoryManager) {
        this.factoryManager = factoryManager;
    }

    @Override
    protected FactoryManager getFactoryManager() {
        return factoryManager;
    }

    @Override
    protected ShapeGlyph<Group> doBuild(final Shape<?> shape) {
        
        final ShapeView<?> view = shape.getShapeView();

        double x = 0;
        double y = 0;
        
        if ( view instanceof HasSize ) {
            
            ((HasSize) view).setSize( width, height );
            
        } else  if ( view instanceof HasRadius ) {
            
            final double radius = width / 2;
            ((HasRadius) view).setRadius( radius );
            x = radius;
            y = radius;
            
        }

        if ( view instanceof HasTitle ) {
            
            final HasTitle hasTitle = (HasTitle) view;
            hasTitle.setTitle( null );
            
        }

        Group group = null;
        
        if ( view instanceof AbstractShapeView) {

            group = ((AbstractShapeView) view).getGroup();

        } else if ( view instanceof AbstractConnectorView) {

            final IPrimitive<?> line = ((AbstractConnectorView) view).getDecoratableLine();
            group = new Group().add( line );

        }

        if ( null == group ) {
            
            throw new RuntimeException( "Shape view [" + view.toString() + "] not supported for " +
                    "this shape glyph builder [" + this.getClass().getName() );
            
        }
        
        final Group result = new Group();
        
        result.add( group.copy().setX( x ).setY( y ) );

        return new LienzoShapeGlyph( result, width, height );
        
    }

   
}
