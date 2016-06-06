package org.wirez.basicset.shape.proxy;

import org.wirez.basicset.definition.Rectangle;

public final class RectangleProxy implements org.wirez.shapes.proxy.RectangleProxy<Rectangle> {

    @Override
    public double getWidth( final Rectangle element ) {
        return element.getWidth().getValue();
    }

    @Override
    public double getHeight( final Rectangle element ) {
        return element.getHeight().getValue();
    }

    @Override
    public String getNamePropertyValue( final Rectangle element ) {
        return element.getName().getValue();
    }

    @Override
    public String getBackgroundColor( final Rectangle element ) {
        return element.getBackgroundSet().getBgColor().getValue();
    }

    @Override
    public String getBorderColor( final Rectangle element ) {
        return element.getBackgroundSet().getBorderColor().getValue();
    }

    @Override
    public double getBorderSize( final Rectangle element ) {
        return element.getBackgroundSet().getBorderSize().getValue();
    }

    @Override
    public String getFontFamily( final Rectangle element ) {
        return element.getFontSet().getFontFamily().getValue();
    }

    @Override
    public String getFontColor( final Rectangle element ) {
        return element.getFontSet().getFontColor().getValue();
    }

    @Override
    public double getFontSize( final Rectangle element ) {
        return element.getFontSet().getFontSize().getValue();
    }

    @Override
    public double getFontBorderSize( final Rectangle element ) {
        return element.getFontSet().getFontBorderSize().getValue();
    }

    @Override
    public String getDescription( final Rectangle element ) {
        return Rectangle.title;
    }

    @Override
    public String getGlyphBackgroundColor( final Rectangle element ) {
        return Rectangle.RectangleBuilder.COLOR;
    }
    
}
