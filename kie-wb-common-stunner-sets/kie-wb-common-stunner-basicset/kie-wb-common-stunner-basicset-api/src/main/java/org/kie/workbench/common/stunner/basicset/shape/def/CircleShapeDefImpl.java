package org.kie.workbench.common.stunner.basicset.shape.def;

import org.kie.workbench.common.stunner.basicset.definition.Circle;
import org.kie.workbench.common.stunner.core.definition.shape.AbstractShapeDef;
import org.kie.workbench.common.stunner.shapes.def.CircleShapeDef;

public final class CircleShapeDefImpl
        extends AbstractShapeDef<Circle>
        implements CircleShapeDef<Circle> {

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
    public double getBackgroundAlpha( final Circle element ) {
        return 1;
    }

    @Override
    public double getBorderAlpha( final Circle element ) {
        return 1;
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
