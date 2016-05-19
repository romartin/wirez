package org.wirez.core.client.shape.view;

import org.wirez.core.api.FactoryManager;
import org.wirez.core.graph.Element;
import org.wirez.core.graph.content.view.View;
import org.wirez.core.util.UUID;
import org.wirez.core.client.shape.MutableShape;
import org.wirez.core.client.shape.MutationContext;
import org.wirez.core.client.shape.Shape;
import org.wirez.core.client.shape.factory.ShapeFactory;

public abstract class AbstractShapeGlyphBuilder<G> implements ShapeGlyphBuilder<G> {
    
    protected String id;
    protected ShapeFactory factory;
    protected double width;
    protected double height;

    protected abstract FactoryManager getFactoryManager();
    
    protected abstract ShapeGlyph<G> doBuild( Shape<?> shape );
    
    @Override
    public ShapeGlyphBuilder<G> definition(final String id) {
        this.id = id;
        return this;
    }

    @Override
    public ShapeGlyphBuilder<G> factory(final ShapeFactory factory) {
        this.factory = factory;
        return this;
    }

    @Override
    public ShapeGlyphBuilder<G> width( final double width ) {
        this.width = width;
        return this;
    }

    @Override
    public ShapeGlyphBuilder<G> height( final double height ) {
        this.height = height;
        return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public ShapeGlyph<G> build() {

        final Element<View<Object>> element = getFactoryManager().newElement(UUID.uuid(), id );
        final Object definition = element.getContent().getDefinition();

        Shape<?> shape = factory.build( definition, null );

        if ( shape instanceof MutableShape ) {

            ((MutableShape) shape).applyProperties( element, MutationContext.STATIC );

        }
        
        return doBuild( shape );
    }

}
