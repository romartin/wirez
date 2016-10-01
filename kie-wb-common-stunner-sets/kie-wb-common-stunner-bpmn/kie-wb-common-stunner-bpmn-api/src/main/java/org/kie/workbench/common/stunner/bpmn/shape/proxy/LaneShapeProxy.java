package org.kie.workbench.common.stunner.bpmn.shape.proxy;

import org.kie.workbench.common.stunner.bpmn.definition.Lane;
import org.kie.workbench.common.stunner.shapes.proxy.AbstractBasicShapeProxy;
import org.kie.workbench.common.stunner.shapes.proxy.RectangleProxy;

public final class LaneShapeProxy
        extends AbstractBasicShapeProxy<Lane>
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
        return element.getDimensionsSet().getWidth().getValue();
    }

    @Override
    public double getHeight( final Lane element ) {
        return element.getDimensionsSet().getHeight().getValue();
    }

}
