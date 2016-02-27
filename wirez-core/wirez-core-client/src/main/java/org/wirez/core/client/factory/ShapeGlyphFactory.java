package org.wirez.core.client.factory;

import org.wirez.core.client.view.ShapeGlyph;

public interface ShapeGlyphFactory {

    /**
     * Builds the shape glyph using defaults.
     */
    ShapeGlyph build();

    /**
     * Builds the shape glyph for a given bounds size.
     */
    ShapeGlyph build(double width, double height);
    
}
