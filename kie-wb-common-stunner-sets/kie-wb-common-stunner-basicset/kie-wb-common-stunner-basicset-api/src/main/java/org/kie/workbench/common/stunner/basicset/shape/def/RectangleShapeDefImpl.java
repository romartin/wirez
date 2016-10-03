package org.kie.workbench.common.stunner.basicset.shape.def;

import org.kie.workbench.common.stunner.basicset.definition.Rectangle;
import org.kie.workbench.common.stunner.core.definition.shape.AbstractShapeDef;
import org.kie.workbench.common.stunner.shapes.def.RectangleShapeDef;

public final class RectangleShapeDefImpl
        extends AbstractShapeDef<Rectangle>
        implements RectangleShapeDef<Rectangle> {

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
    public double getBackgroundAlpha( final Rectangle element ) {
        return 1;
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
    public double getBorderAlpha( final Rectangle element ) {
        return 1;
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
    public String getGlyphDescription(final Rectangle element ) {
        return Rectangle.title;
    }

    @Override
    public String getGlyphBackgroundColor( final Rectangle element ) {
        return Rectangle.RectangleBuilder.COLOR;
    }
    
}
