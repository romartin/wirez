package org.wirez.client.shapes.view;

import com.ait.lienzo.client.core.shape.MultiPath;
import com.ait.lienzo.client.core.shape.Shape;
import com.ait.lienzo.client.core.shape.wires.WiresManager;

/**
 * This shape handles two primitives added as wires shape's children: 
 * - The visible primitive
 * - The decorator primitive
 */
public abstract class AbstractPrimitiveShapeView<T> extends AbstractDecoratableShapeView<T> {
    
    public AbstractPrimitiveShapeView(final MultiPath path,
                                      final WiresManager manager) {
        super( path, manager );
    }

    protected abstract Shape<?> getPrimitive();

    protected abstract Shape<?> createChildren();
    
    protected void initialize() {
        super.initialize();
        getPath().setFillAlpha(0).setStrokeAlpha(0);
        getPath().moveToTop();
    }

    @Override
    protected Shape<?> createDecorator() {
        return createChildren();
    }

    protected Shape<?> getShape() {
        return getPrimitive();
    }

    protected  T setRadius(final double radius) {
        if (radius > 0) {
            
            final double size = radius * 2;
            updatePath( size, size );
            getShape().getAttributes().setRadius( radius );
            
            if ( null != decorator ) {

                decorator.getAttributes().setRadius( radius );
                
            }

            doMoveChildren( size, size );

            super.updateFillGradient( radius * 2, radius * 2 );
            
        }
        return (T) this;
    }

    protected T setSize(final double width, final double height) {
        updatePath( width, height );
        getShape().getAttributes().setWidth( width );
        getShape().getAttributes().setHeight( height );
        
        if ( null != decorator ) {

            decorator.getAttributes().setWidth( width );
            decorator.getAttributes().setHeight( height );
            
        }

        doMoveChildren( width, height );
        
        super.updateFillGradient( width, height );
        
        return (T) this;
    }
    
    protected void doMoveChildren(final double width, final double height) {
        
    }
    
    protected void updatePath( final double width, 
                               final double height ) {

        final double x = getPath().getX();
        final double y = getPath().getY();
        getPath().clear().rect(x, y, width, height);
    }

}
