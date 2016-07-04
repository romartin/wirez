package org.wirez.core.client.canvas;

public abstract class AbstractCanvasGrid implements CanvasGrid {

    private final double primSize;
    private final double primAlpha;
    private final String primColor;
    private final double secSize;
    private final double secAlpha;
    private final String secColor;

    public AbstractCanvasGrid( final double primSize,
                               final double primAlpha,
                               final String primColor,
                               final double secSize,
                               final double secAlpha,
                               final String secColor ) {
        this.primSize = primSize;
        this.primAlpha = primAlpha;
        this.primColor = primColor;
        this.secSize = secSize;
        this.secAlpha = secAlpha;
        this.secColor = secColor;
    }

    @Override
    public double getPrimarySize() {
        return primSize;
    }

    @Override
    public double getPrimaryAlpha() {
        return primAlpha;
    }

    @Override
    public String getPrimaryColor() {
        return primColor;
    }

    @Override
    public double getSecondarySize() {
        return secSize;
    }

    @Override
    public double getSecondaryAlpha() {
        return secAlpha;
    }

    @Override
    public String getSecondaryColor() {
        return secColor;
    }

}
