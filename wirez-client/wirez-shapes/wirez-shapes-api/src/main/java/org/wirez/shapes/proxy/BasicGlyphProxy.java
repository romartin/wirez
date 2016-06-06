package org.wirez.shapes.proxy;

import org.wirez.core.definition.shape.GlyphProxy;

public interface BasicGlyphProxy<W> extends GlyphProxy<W> {
    
    String getGlyphBackgroundColor( W element );
    
}
