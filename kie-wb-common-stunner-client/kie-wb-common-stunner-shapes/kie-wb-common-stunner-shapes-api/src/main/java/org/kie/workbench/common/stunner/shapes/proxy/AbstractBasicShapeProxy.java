package org.kie.workbench.common.stunner.shapes.proxy;

import org.kie.workbench.common.stunner.core.definition.shape.AbstractShapeProxy;

public abstract class AbstractBasicShapeProxy<W> extends AbstractShapeProxy<W> implements BasicShapeProxy<W> {

    @Override
    public double getBackgroundAlpha( final W element ) {
        return 1;
    }

    @Override
    public double getBorderAlpha( final W element ) {
        return 1;
    }

    @Override
    public String getBackgroundColor( final W element ) {
        // By default background color does not changes as model updates.
        return null;
    }

    @Override
    public String getBorderColor( final W element ) {
        // By default border color does not changes as model updates.
        return null;
    }

    @Override
    public double getBorderSize( final W element ) {
        // By default border does not changes as model updates.
        return 0;
    }
}
