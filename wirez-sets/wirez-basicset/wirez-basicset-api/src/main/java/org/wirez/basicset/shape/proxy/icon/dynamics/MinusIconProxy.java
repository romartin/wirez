package org.wirez.basicset.shape.proxy.icon.dynamics;

import org.wirez.basicset.definition.icon.dynamics.MinusIcon;

public final class MinusIconProxy 
        extends AbstractDynamicIconProxy<MinusIcon> 
        implements org.wirez.shapes.proxy.icon.dynamics.MinusIconProxy<MinusIcon> {
    
    @Override
    public String getGlyphBackgroundColor( final MinusIcon definition ) {
        return MinusIcon.COLOR;
    }

    @Override
    public String getGlyphDescription(final MinusIcon definition ) {
        return MinusIcon.description;
    }
    
}
