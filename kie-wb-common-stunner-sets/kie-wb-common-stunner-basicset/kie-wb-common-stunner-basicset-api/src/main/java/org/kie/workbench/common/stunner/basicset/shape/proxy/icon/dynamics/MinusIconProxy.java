package org.kie.workbench.common.stunner.basicset.shape.proxy.icon.dynamics;

import org.kie.workbench.common.stunner.basicset.definition.icon.dynamics.MinusIcon;

public final class MinusIconProxy 
        extends AbstractDynamicIconProxy<MinusIcon> 
        implements org.kie.workbench.common.stunner.shapes.proxy.icon.dynamics.MinusIconProxy<MinusIcon> {
    
    @Override
    public String getGlyphBackgroundColor( final MinusIcon definition ) {
        return MinusIcon.COLOR;
    }

    @Override
    public String getGlyphDescription(final MinusIcon definition ) {
        return MinusIcon.description;
    }
    
}
