package org.wirez.core.client.shape;

import org.wirez.core.graph.Edge;
import org.wirez.core.graph.Node;
import org.wirez.core.graph.content.view.View;
import org.wirez.core.client.shape.view.ShapeView;

import java.util.LinkedList;
import java.util.List;

public abstract class AbstractCompositeShape<W, E extends Node<View<W>, Edge>, V extends ShapeView> 
    extends AbstractShape<W, E, V>
    implements HasChildren<AbstractShape<W, Node<View<W>, Edge>, ?>>{

    protected final List<AbstractShape<W, Node<View<W>, Edge>, ?>> children = new LinkedList<AbstractShape<W, Node<View<W>, Edge>, ?>>();

    public AbstractCompositeShape(final V view) {
        super( view );
    }

    @Override
    @SuppressWarnings("unchecked")
    public void addChild(final AbstractShape<W, Node<View<W>, Edge>, ?> child, 
                         final Layout layout) {

        final HasChildren<ShapeView<?>> view = 
                (HasChildren<ShapeView<?>>) getShapeView();


        children.add( child );
        
        view.addChild( child.getShapeView(), layout );
        
    }

    @Override
    @SuppressWarnings("unchecked")
    public void removeChild(final AbstractShape<W, Node<View<W>, Edge>, ?> child) {
        final HasChildren<ShapeView<?>> view =
                (HasChildren<ShapeView<?>>) getShapeView();

        children.remove( child );
        
        view.removeChild( child.getShapeView() );
        
    }

    @Override
    public void applyProperties(final E element, 
                                final MutationContext mutationContext) {
        super.applyProperties(element, mutationContext);

        // Apply properties to children shapes.
        for ( final AbstractShape<W, Node<View<W>, Edge>, ?> child : children ) {
            child.applyProperties( element, mutationContext );
        }
        
    }

    @Override
    public void applyProperty(final E element, 
                              final String propertyId, 
                              final Object value, 
                              final MutationContext mutationContext) {
        super.applyProperty(element, propertyId, value, mutationContext);

        // Apply property to children shapes.
        for ( final AbstractShape<W, Node<View<W>, Edge>, ?> child : children ) {
            child.applyProperty( element, propertyId, value, mutationContext );
        }
        
    }

    @Override
    protected void doDestroy() {
        
        if ( !children.isEmpty() ) {

            for ( final AbstractShape<W, Node<View<W>, Edge>, ?> child : children ) {

                child.destroy();

            }

        }

        children.clear();
        
    }
    
}
