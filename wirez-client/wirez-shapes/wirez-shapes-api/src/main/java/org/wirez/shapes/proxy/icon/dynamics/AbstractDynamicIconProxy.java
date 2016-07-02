package org.wirez.shapes.proxy.icon.dynamics;

import org.wirez.shapes.proxy.AbstractBasicGlyphProxy;

public abstract class AbstractDynamicIconProxy<W>  extends AbstractBasicGlyphProxy<W>
        implements DynamicIconProxy<W> {

    @Override
    public double getBackgroundAlpha( final W element ) {
        return 1;
    }

    @Override
    public double getBorderAlpha( final W element ) {
        return 1;
    }
}
