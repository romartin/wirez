package org.wirez.basicset.shape.proxy;

import org.wirez.basicset.definition.Ring;
import org.wirez.shapes.proxy.AbstractBasicShapeProxy;

public final class RingProxy
        extends AbstractBasicShapeProxy<Ring>
        implements org.wirez.shapes.proxy.RingProxy<Ring> {

    @Override
    public String getNamePropertyValue( final Ring element ) {
        return element.getName().getValue();
    }

    @Override
    public String getBackgroundColor( final Ring element ) {
        return element.getBackgroundSet().getBgColor().getValue();
    }

    @Override
    public String getBorderColor( final Ring element ) {
        return element.getBackgroundSet().getBorderColor().getValue();
    }

    @Override
    public double getBorderSize( final Ring element ) {
        return element.getBackgroundSet().getBorderSize().getValue();
    }

    @Override
    public String getFontFamily( final Ring element ) {
        return element.getFontSet().getFontFamily().getValue();
    }

    @Override
    public String getFontColor( final Ring element ) {
        return element.getFontSet().getFontColor().getValue();
    }

    @Override
    public double getFontSize( final Ring element ) {
        return element.getFontSet().getFontSize().getValue();
    }

    @Override
    public double getFontBorderSize( final Ring element ) {
        return element.getFontSet().getFontBorderSize().getValue();
    }

    @Override
    public double getOuterRadius( final Ring element ) {
        return element.getOuterRadius().getValue();
    }

    @Override
    public double getInnerRadius( final Ring element ) {
        return element.getInnerRadius().getValue();
    }

    @Override
    public String getGlyphDescription(final Ring element ) {
        return Ring.title;
    }

    @Override
    public String getGlyphBackgroundColor( final Ring elementRing) {
        return Ring.RingBuilder.COLOR;
    }
    
}
