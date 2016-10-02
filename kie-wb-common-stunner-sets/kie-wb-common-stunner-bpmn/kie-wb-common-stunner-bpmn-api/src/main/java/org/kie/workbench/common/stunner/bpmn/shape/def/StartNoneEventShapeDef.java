package org.kie.workbench.common.stunner.bpmn.shape.def;

import org.kie.workbench.common.stunner.bpmn.definition.StartNoneEvent;
import org.kie.workbench.common.stunner.core.definition.shape.AbstractShapeDef;
import org.kie.workbench.common.stunner.shapes.def.CircleShapeDef;

public final class StartNoneEventShapeDef
        extends AbstractShapeDef<StartNoneEvent>
        implements CircleShapeDef<StartNoneEvent> {
    
    @Override
    public double getRadius( final StartNoneEvent element ) {
        return element.getDimensionsSet().getRadius().getValue();
    }

    @Override
    public String getBackgroundColor( final StartNoneEvent element ) {
        return element.getBackgroundSet().getBgColor().getValue();
    }

    @Override
    public double getBackgroundAlpha( final StartNoneEvent element ) {
        return 1;
    }

    @Override
    public String getBorderColor( final StartNoneEvent element ) {
        return element.getBackgroundSet().getBorderColor().getValue();
    }

    @Override
    public double getBorderSize( final StartNoneEvent element ) {
        return element.getBackgroundSet().getBorderSize().getValue();
    }

    @Override
    public double getBorderAlpha( final StartNoneEvent element ) {
        return 1;
    }

    @Override
    public String getFontFamily( final StartNoneEvent element ) {
        return element.getFontSet().getFontFamily().getValue();
    }

    @Override
    public String getFontColor( final StartNoneEvent element ) {
        return element.getFontSet().getFontColor().getValue();
    }

    @Override
    public double getFontSize( final StartNoneEvent element ) {
        return element.getFontSet().getFontSize().getValue();
    }

    @Override
    public String getNamePropertyValue( final StartNoneEvent element ) {
        return element.getGeneral().getName().getValue();
    }

    @Override
    public double getFontBorderSize( final StartNoneEvent element ) {
        return element.getFontSet().getFontBorderSize().getValue();
    }

    @Override
    public String getGlyphBackgroundColor( final StartNoneEvent element ) {
        return StartNoneEvent.StartNoneEventBuilder.COLOR;
    }

    @Override
    public String getGlyphDescription(final StartNoneEvent element ) {
        return StartNoneEvent.description;
    }
}
