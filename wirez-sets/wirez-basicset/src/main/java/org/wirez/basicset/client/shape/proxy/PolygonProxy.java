package org.wirez.basicset.client.shape.proxy;

import org.wirez.basicset.api.Polygon;

public final class PolygonProxy implements org.wirez.client.shapes.proxy.PolygonProxy<Polygon> {
    
    @Override
    public double getRadius( final Polygon element ) {
        return element.getRadius().getValue();
    }

    @Override
    public String getNamePropertyValue( final Polygon element ) {
        return element.getName().getValue();
    }

    @Override
    public String getBackgroundColor( final Polygon element ) {
        return element.getBackgroundSet().getBgColor().getValue();
    }

    @Override
    public String getBorderColor( final Polygon element ) {
        return element.getBackgroundSet().getBorderColor().getValue();
    }

    @Override
    public double getBorderSize( final Polygon element ) {
        return element.getBackgroundSet().getBorderSize().getValue();
    }

    @Override
    public String getFontFamily( final Polygon element ) {
        return element.getFontSet().getFontFamily().getValue();
    }

    @Override
    public String getFontColor( final Polygon element ) {
        return element.getFontSet().getFontColor().getValue();
    }

    @Override
    public double getFontSize( final Polygon element ) {
        return element.getFontSet().getFontSize().getValue();
    }

    @Override
    public double getFontBorderSize( final Polygon element ) {
        return element.getFontSet().getFontBorderSize().getValue();
    }

    @Override
    public String getDescription() {
        return Polygon.title;
    }

    @Override
    public String getGlyphBackgroundColor() {
        return Polygon.COLOR;
    }
    
}
