package org.kie.workbench.common.stunner.core.client.shape.view;

import org.kie.workbench.common.stunner.core.client.shape.factory.ShapeFactory;
import org.kie.workbench.common.stunner.core.definition.shape.GlyphDef;

public interface ShapeGlyphBuilder<G> {

    ShapeGlyphBuilder<G> definition( String id );

    ShapeGlyphBuilder<G> glyphProxy( GlyphDef<?> glyphProxy, String id );

    ShapeGlyphBuilder<G> factory( ShapeFactory factory );
    ;
    ShapeGlyphBuilder<G> width( double width );

    ShapeGlyphBuilder<G> height( double height );
    
    ShapeGlyph<G> build();
    
}
