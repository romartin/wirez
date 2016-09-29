package org.kie.workbench.common.stunner.shapes.proxy;

import org.kie.workbench.common.stunner.core.definition.shape.GlyphProxy;

public interface BasicGlyphProxy<W> extends GlyphProxy<W> {
    
    String getGlyphBackgroundColor( W element );
    
}
