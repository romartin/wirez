package org.kie.workbench.common.stunner.basicset.shape.def;

import org.kie.workbench.common.stunner.basicset.definition.Polygon;
import org.kie.workbench.common.stunner.core.definition.shape.AbstractShapeDef;
import org.kie.workbench.common.stunner.shapes.def.PolygonShapeDef;

public final class PolygonShapeDefImpl
        extends AbstractShapeDef<Polygon>
        implements PolygonShapeDef<Polygon> {
    
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
    public double getBackgroundAlpha( final Polygon element ) {
        return 1;
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
    public double getBorderAlpha( final Polygon element ) {
        return 1;
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
