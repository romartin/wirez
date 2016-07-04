package org.wirez.shapes.proxy;

public abstract class AbstractBasicDynamicShapeProxy<W> extends AbstractBasicShapeProxy<W> implements BasicDynamicShapeProxy<W> {

    @Override
    public double getBackgroundAlpha( final W element ) {
        return 1;
    }

    @Override
    public double getBorderAlpha( final W element ) {
        return 1;
    }

}
