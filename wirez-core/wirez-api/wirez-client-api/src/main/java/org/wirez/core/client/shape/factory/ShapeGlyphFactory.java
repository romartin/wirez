package org.wirez.core.client.shape.factory;

import org.wirez.core.client.shape.view.ShapeGlyph;

public interface ShapeGlyphFactory {

    /**
     * Builds the shape glyph using defaults.
     */
    ShapeGlyph build(final String definitionId);

    /**
     * Builds the shape glyph for a given bounds size.
     */
    ShapeGlyph build(final String definitionId, double width, double height);
    
}
