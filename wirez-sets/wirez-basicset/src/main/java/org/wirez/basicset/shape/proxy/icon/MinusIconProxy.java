package org.wirez.basicset.shape.proxy.icon;

import org.wirez.basicset.definition.icon.MinusIcon;

public final class MinusIconProxy 
        extends AbstractIconProxy<MinusIcon> 
        implements org.wirez.shapes.proxy.icon.MinusIconProxy<MinusIcon> {
    
    @Override
    public String getGlyphBackgroundColor() {
        return MinusIcon.COLOR;
    }

    @Override
    public String getDescription() {
        return MinusIcon.description;
    }
    
}
