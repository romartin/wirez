package org.wirez.basicset.shape.proxy;

import org.wirez.basicset.definition.Polygon;
import org.wirez.shapes.proxy.AbstractBasicDynamicShapeProxy;

public final class PolygonProxy
        extends AbstractBasicDynamicShapeProxy<Polygon>
        implements org.wirez.shapes.proxy.PolygonProxy<Polygon> {
    
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
    public String getGlyphDescription(final Polygon element ) {
        return Polygon.title;
    }

    @Override
    public String getGlyphBackgroundColor( final Polygon element ) {
        return Polygon.PolygonBuilder.COLOR;
    }
    
}
