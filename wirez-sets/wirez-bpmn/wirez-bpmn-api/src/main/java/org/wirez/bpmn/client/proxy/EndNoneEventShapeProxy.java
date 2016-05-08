package org.wirez.bpmn.client.proxy;

import org.wirez.bpmn.api.EndNoneEvent;
import org.wirez.client.shapes.proxy.CircleProxy;

public final class EndNoneEventShapeProxy implements CircleProxy<EndNoneEvent> {
    
    @Override
    public double getRadius( final EndNoneEvent element ) {
        return element.getRadius().getValue();
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
    public String getGlyphBackgroundColor() {
        return EndNoneEvent.COLOR;
    }

    @Override
    public String getDescription() {
        return EndNoneEvent.description;
    }
}
