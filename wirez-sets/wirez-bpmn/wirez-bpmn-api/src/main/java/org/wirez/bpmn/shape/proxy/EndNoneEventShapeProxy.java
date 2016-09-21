package org.wirez.bpmn.shape.proxy;

import org.wirez.bpmn.definition.EndNoneEvent;
import org.wirez.shapes.proxy.AbstractBasicDynamicShapeProxy;
import org.wirez.shapes.proxy.CircleProxy;

public final class EndNoneEventShapeProxy
        extends AbstractBasicDynamicShapeProxy<EndNoneEvent>
        implements CircleProxy<EndNoneEvent> {
    
    @Override
    public double getRadius( final EndNoneEvent element ) {
        return element.getDimensionsSet().getRadius().getValue();
    }

    @Override
    public String getBackgroundColor( final EndNoneEvent element ) {
        return element.getBackgroundSet().getBgColor().getValue();
    }

    @Override
    public String getBorderColor( final EndNoneEvent element ) {
        return element.getBackgroundSet().getBorderColor().getValue();
    }

    @Override
    public double getBorderSize( final EndNoneEvent element ) {
        return element.getBackgroundSet().getBorderSize().getValue();
    }

    @Override
    public String getFontFamily( final EndNoneEvent element ) {
        return element.getFontSet().getFontFamily().getValue();
    }

    @Override
    public String getFontColor( final EndNoneEvent element ) {
        return element.getFontSet().getFontColor().getValue();
    }

    @Override
    public double getFontSize( final EndNoneEvent element ) {
        return element.getFontSet().getFontSize().getValue();
    }

    @Override
    public String getNamePropertyValue( final EndNoneEvent element ) {
        return element.getGeneral().getName().getValue();
    }

    @Override
    public double getFontBorderSize( final EndNoneEvent element ) {
        return element.getFontSet().getFontBorderSize().getValue();
    }

    @Override
    public String getGlyphBackgroundColor( final EndNoneEvent element ) {
        return EndNoneEvent.EndNoneEventBuilder.COLOR;
    }

    @Override
    public String getGlyphDescription(final EndNoneEvent element ) {
        return EndNoneEvent.description;
    }
}
