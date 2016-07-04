package org.wirez.core.client.shape.view;

import org.wirez.core.client.shape.Shape;
import org.wirez.core.client.shape.factory.ShapeFactory;
import org.wirez.core.definition.shape.GlyphProxy;

public interface ShapeGlyphBuilder<G> {

    ShapeGlyphBuilder<G> definition( String id );

    ShapeGlyphBuilder<G> glyphProxy( GlyphProxy<?> glyphProxy, String id );

    ShapeGlyphBuilder<G> factory( ShapeFactory factory );
    ;
    ShapeGlyphBuilder<G> width( double width );

    ShapeGlyphBuilder<G> height( double height );
    
    ShapeGlyph<G> build();
    
}
