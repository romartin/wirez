package org.wirez.shapes.proxy;

public abstract class WrappedBasicNamedShapeProxy<W>
        extends AbstractBasicShapeProxy<W>
        implements BasicNamedShapeProxy<W> {
    
    protected final BasicNamedShapeProxy<W> parent;

    public WrappedBasicNamedShapeProxy(final BasicNamedShapeProxy<W> parent ) {
        this.parent = parent;
    }

    @Override
    public String getNamePropertyValue( final W element ) {
        return null;
    }

    @Override
    public String getFontFamily( final W element ) {
        return parent.getFontFamily( element );
    }

    @Override
    public String getFontColor( final W element ) {
        return parent.getFontColor( element );
    }

    @Override
    public double getFontSize( final W element ) {
        return parent.getFontSize( element );
    }

    @Override
    public double getFontBorderSize( final W element ) {
        return parent.getFontBorderSize( element );
    }

    @Override
    public String getBackgroundColor( final W element ) {
        return parent.getBackgroundColor( element );
    }

    @Override
    public double getBackgroundAlpha( final W element ) {
        return parent.getBackgroundAlpha(  element );
    }

    @Override
    public String getBorderColor( final W element ) {
        return parent.getBorderColor( element );
    }

    @Override
    public double getBorderSize( final W element ) {
        return parent.getBorderSize( element );
    }

    @Override
    public double getBorderAlpha( final W element ) {
        return parent.getBorderAlpha( element );
    }

    @Override
    public String getGlyphBackgroundColor( final W element ) {
        return null;
    }

    @Override
    public String getGlyphDescription(final W element) {
        return null;
    }
    
}
