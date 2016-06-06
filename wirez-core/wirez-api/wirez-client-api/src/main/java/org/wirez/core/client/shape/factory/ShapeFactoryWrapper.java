package org.wirez.core.client.shape.factory;

import org.wirez.core.client.shape.Shape;
import org.wirez.core.client.shape.view.ShapeGlyph;

public abstract class ShapeFactoryWrapper<W, C, S extends Shape> implements ShapeFactory<W, C, S> {

    protected abstract ShapeFactory getFactory();

    @Override
    public boolean accepts(final String definitionId) {
        return getFactory().accepts( definitionId );
    }

    @Override
    public String getDescription(final String definitionId) {
        return getFactory().getDescription( definitionId );
    }

    @Override
    @SuppressWarnings("unchecked")
    public S build(final W definition,
                   final C context) {
        return (S) getFactory().build( definition, context );
    }

    @Override
    public ShapeGlyph glyph(final String definitionId,
                            final double width,
                            final double height) {
        return getFactory().glyph( definitionId, width, height );
    }

}
