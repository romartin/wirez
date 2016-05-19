package org.wirez.shapes.client.view;

import com.ait.lienzo.client.core.shape.MultiPath;
import com.ait.lienzo.client.core.shape.Shape;
import com.ait.lienzo.client.core.shape.wires.WiresManager;
import org.wirez.core.client.shape.view.HasDecorators;

import java.util.Collection;
import java.util.LinkedList;

/**
 * Provides an abstract HasDecorators implementation based on a single primitive. HasDecorators interface used for different purposes such as 
 *      selection, highlight, etc.
 */
public abstract class AbstractDecoratableShapeView<T> extends org.wirez.shapes.client.view.BasicShapeView<T> 
        implements HasDecorators<Shape> {

    protected final Collection<Shape> decorators = new LinkedList<>();
    protected Shape<?> decorator = null;
    
    public AbstractDecoratableShapeView(final MultiPath path,
                                        final WiresManager manager) {
        super(path, manager);
        initialize();
    }

    protected abstract Shape<?> createDecorator();
    
    protected void initialize() {
        
        createDecorators();
        
    }
    
    protected void createDecorators() {

        if ( null != decorator ) {

            decorator.removeFromParent();

        }

        decorators.clear();

        decorator = createDecorator();
        
        if ( null != decorator ) {
        
            decorator
                .setStrokeWidth(0)
                .setFillAlpha(0)
                .setStrokeAlpha(0);
        
            decorators.add( decorator );
        }
        
    }
    
    protected Shape<?> getShape() {
        return getPath();
    }

    @Override
    public Collection<Shape> getDecorators() {
        return decorators;
    }

    @Override
    protected void doDestroy() {
        
        if ( null != decorator ) {
            decorator.removeFromParent();
            decorator = null;
        }
        
        decorators.clear();
    }
    
}
