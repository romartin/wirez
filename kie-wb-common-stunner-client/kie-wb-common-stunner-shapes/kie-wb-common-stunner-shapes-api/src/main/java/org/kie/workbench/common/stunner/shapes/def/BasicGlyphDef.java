package org.kie.workbench.common.stunner.shapes.def;

import org.kie.workbench.common.stunner.core.definition.shape.GlyphDef;

public interface BasicGlyphDef<W> extends GlyphDef<W> {
    
    String getGlyphBackgroundColor( W element );
    
}
