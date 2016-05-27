package org.wirez.basicset.shape.proxy.icon.dynamics;

import org.wirez.basicset.definition.icon.dynamics.MinusIcon;

public final class MinusIconProxy 
        extends AbstractDynamicIconProxy<MinusIcon> 
        implements org.wirez.shapes.proxy.icon.dynamics.MinusIconProxy<MinusIcon> {
    
    @Override
    public String getGlyphBackgroundColor() {
        return MinusIcon.COLOR;
    }

    @Override
    public String getDescription() {
        return MinusIcon.description;
    }
    
}
