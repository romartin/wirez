package org.kie.workbench.common.stunner.shapes.proxy.icon.dynamics;

import org.kie.workbench.common.stunner.shapes.proxy.AbstractBasicGlyphProxy;

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
