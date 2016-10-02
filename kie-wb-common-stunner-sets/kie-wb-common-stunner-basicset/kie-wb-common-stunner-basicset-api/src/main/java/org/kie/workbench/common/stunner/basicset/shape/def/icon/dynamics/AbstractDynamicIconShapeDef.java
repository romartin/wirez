package org.kie.workbench.common.stunner.basicset.shape.def.icon.dynamics;

import org.kie.workbench.common.stunner.basicset.definition.icon.dynamics.DynamicIcon;
import org.kie.workbench.common.stunner.core.definition.shape.AbstractShapeDef;
import org.kie.workbench.common.stunner.shapes.def.icon.dynamics.IconShapeDef;

public abstract class AbstractDynamicIconShapeDef<I extends DynamicIcon>
        extends AbstractShapeDef<I>
        implements IconShapeDef<I> {
    
    @Override
    public double getWidth( final I element ) {
        return element.getWidth().getValue();
    }

    @Override
    public double getHeight( final I element ) {
        return element.getHeight().getValue();
    }

    @Override
    public String getBackgroundColor( final I element ) {
        return element.getBackgroundSet().getBgColor().getValue();
    }

    @Override
    public String getBorderColor( final I element ) {
        return element.getBackgroundSet().getBorderColor().getValue();
    }

    @Override
    public double getBorderSize( final I element ) {
        return element.getBackgroundSet().getBorderSize().getValue();
    }

    @Override
    public double getBackgroundAlpha( final I element ) {
        return 1;
    }

    @Override
    public double getBorderAlpha( final I element ) {
        return 1;
    }
}
