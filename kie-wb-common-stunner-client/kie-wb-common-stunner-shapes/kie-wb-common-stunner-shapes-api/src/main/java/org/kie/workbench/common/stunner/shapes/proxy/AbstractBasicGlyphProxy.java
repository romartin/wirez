package org.kie.workbench.common.stunner.shapes.proxy;

import org.kie.workbench.common.stunner.core.definition.shape.AbstractBindableGlyphProxy;

public abstract class AbstractBasicGlyphProxy<W> extends AbstractBindableGlyphProxy<W> implements BasicGlyphProxy<W> {

    @Override
    public String getGlyphBackgroundColor( final W element ) {
        return null;
    }


}
