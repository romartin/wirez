package org.kie.workbench.common.stunner.basicset.shape.proxy;

import org.kie.workbench.common.stunner.basicset.definition.Circle;
import org.kie.workbench.common.stunner.shapes.proxy.AbstractBasicDynamicShapeProxy;

public final class CircleProxy
        extends AbstractBasicDynamicShapeProxy<Circle>
        implements org.kie.workbench.common.stunner.shapes.proxy.CircleProxy<Circle> {

    @Override
    public String getNamePropertyValue( final Circle element ) {
        return element.getName().getValue();
    }

    @Override
    public String getBackgroundColor( final Circle element ) {
        return element.getBackgroundSet().getBgColor().getValue();
    }

    @Override
    public String getBorderColor( final Circle element ) {
        return element.getBackgroundSet().getBorderColor().getValue();
    }

    @Override
    public double getBorderSize( final Circle element ) {
        return element.getBackgroundSet().getBorderSize().getValue();
    }

    @Override
    public String getFontFamily( final Circle element ) {
        return element.getFontSet().getFontFamily().getValue();
    }

    @Override
    public String getFontColor( final Circle element ) {
        return element.getFontSet().getFontColor().getValue();
    }

    @Override
    public double getFontSize( final Circle element ) {
        return element.getFontSet().getFontSize().getValue();
    }

    @Override
    public double getFontBorderSize( final Circle element ) {
        return element.getFontSet().getFontBorderSize().getValue();
    }

    @Override
    public double getRadius( final Circle element ) {
        return element.getRadius().getValue();
    }

    @Override
    public String getGlyphDescription(final Circle element ) {
        return Circle.title;
    }

    @Override
    public String getGlyphBackgroundColor( final Circle element ) {
        return Circle.CircleBuilder.COLOR;
    }
    
}
