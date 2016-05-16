package org.wirez.basicset.client.shape.proxy.icon;

import org.wirez.basicset.api.icon.MinusIcon;

public final class MinusIconProxy 
        extends AbstractIconProxy<MinusIcon> 
        implements org.wirez.client.shapes.proxy.icon.MinusIconProxy<MinusIcon> {
    
    @Override
    public String getGlyphBackgroundColor() {
        return MinusIcon.COLOR;
    }

    @Override
    public String getDescription() {
        return MinusIcon.description;
    }
    
}
