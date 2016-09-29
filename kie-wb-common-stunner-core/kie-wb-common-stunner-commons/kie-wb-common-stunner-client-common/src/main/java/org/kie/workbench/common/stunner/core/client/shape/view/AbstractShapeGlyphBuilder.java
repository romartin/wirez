package org.kie.workbench.common.stunner.core.client.shape.view;

import org.kie.workbench.common.stunner.core.api.FactoryManager;
import org.kie.workbench.common.stunner.core.client.shape.view.ShapeGlyph;
import org.kie.workbench.common.stunner.core.client.shape.view.ShapeGlyphBuilder;
import org.kie.workbench.common.stunner.core.definition.shape.GlyphProxy;
import org.kie.workbench.common.stunner.core.graph.Element;
import org.kie.workbench.common.stunner.core.graph.content.view.View;
import org.kie.workbench.common.stunner.core.util.UUID;
import org.kie.workbench.common.stunner.core.client.shape.MutableShape;
import org.kie.workbench.common.stunner.core.client.shape.MutationContext;
import org.kie.workbench.common.stunner.core.client.shape.Shape;
import org.kie.workbench.common.stunner.core.client.shape.factory.ShapeFactory;

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
    public ShapeGlyphBuilder<G> glyphProxy( final GlyphProxy<?> glyphProxy,
                                            final String id) {

        this.id = glyphProxy.getGlyphDefinitionId( id );
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

        final Element<View<Object>> element =
                ( Element<View<Object>> ) getFactoryManager().newElement(UUID.uuid(), id );

        final Object definition = element.getContent().getDefinition();

        Shape<?> shape = factory.build( definition, null );

        if ( shape instanceof MutableShape ) {

            ((MutableShape) shape).applyProperties( element, MutationContext.STATIC );

        }
        
        return doBuild( shape );
    }

}
