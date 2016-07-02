package org.wirez.bpmn.shape.proxy;

import org.wirez.bpmn.definition.Lane;
import org.wirez.shapes.proxy.AbstractBasicDynamicShapeProxy;
import org.wirez.shapes.proxy.RectangleProxy;

public final class LaneShapeProxy
        extends AbstractBasicDynamicShapeProxy<Lane>
        implements RectangleProxy<Lane> {
    
    @Override
    public String getBackgroundColor( final Lane element ) {
        return element.getBackgroundSet().getBgColor().getValue();
    }

    @Override
    public double getBackgroundAlpha( final Lane element ) {
        return 0.8;
    }

    @Override
    public String getBorderColor( final Lane element ) {
        return element.getBackgroundSet().getBorderColor().getValue();
    }

    @Override
    public double getBorderSize( final Lane element ) {
        return element.getBackgroundSet().getBorderSize().getValue();
    }

    @Override
    public String getFontFamily( final Lane element ) {
        return element.getFontSet().getFontFamily().getValue();
    }

    @Override
    public String getFontColor( final Lane element ) {
        return element.getFontSet().getFontColor().getValue();
    }

    @Override
    public double getFontSize( final Lane element ) {
        return element.getFontSet().getFontSize().getValue();
    }

    @Override
    public String getNamePropertyValue( final Lane element ) {
        return element.getGeneral().getName().getValue();
    }

    @Override
    public double getFontBorderSize( final Lane element ) {
        return element.getFontSet().getFontBorderSize().getValue();
    }

    @Override
    public String getGlyphBackgroundColor( final Lane element ) {
        return element.getBackgroundSet().getBgColor().getValue();
    }
    @Override
    public double getWidth( final Lane element ) {
        return element.getWidth().getValue();
    }

    @Override
    public double getHeight( final Lane element ) {
        return element.getHeight().getValue();
    }

}
