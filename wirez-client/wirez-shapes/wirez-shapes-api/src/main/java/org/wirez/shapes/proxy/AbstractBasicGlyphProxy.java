package org.wirez.shapes.proxy;

import org.wirez.core.definition.shape.AbstractBindableGlyphProxy;
import org.wirez.core.definition.shape.AbstractGlyphProxy;
import org.wirez.shapes.proxy.BasicGlyphProxy;

public abstract class AbstractBasicGlyphProxy<W> extends AbstractBindableGlyphProxy<W> implements BasicGlyphProxy<W> {

    @Override
    public String getGlyphBackgroundColor( final W element ) {
        return null;
    }

}
