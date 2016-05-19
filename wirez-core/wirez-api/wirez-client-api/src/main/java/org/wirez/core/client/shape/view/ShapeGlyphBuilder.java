package org.wirez.core.client.shape.view;

import org.wirez.core.client.shape.Shape;
import org.wirez.core.client.shape.factory.ShapeFactory;

public interface ShapeGlyphBuilder<G> {

    ShapeGlyphBuilder<G> definition( String id );

    ShapeGlyphBuilder<G> factory( ShapeFactory factory );
    ;
    ShapeGlyphBuilder<G> width( double width );

    ShapeGlyphBuilder<G> height( double height );
    
    ShapeGlyph<G> build();
    
}
