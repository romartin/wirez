package org.wirez.shapes.proxy;

// TODO: Add support for text on connectors.
public abstract class AbstractBasicConnectorProxy<W> extends AbstractBasicDynamicShapeProxy<W> implements ConnectorProxy<W> {

    @Override
    public String getNamePropertyValue( final W element ) {
        return null;
    }

    @Override
    public String getFontFamily( final W element ) {
        return null;
    }

    @Override
    public String getFontColor( final W element ) {
        return null;
    }

    @Override
    public double getFontSize( final W element ) {
        return 0;
    }

    @Override
    public double getFontBorderSize( final W element ) {
        return 0;
    }

}
