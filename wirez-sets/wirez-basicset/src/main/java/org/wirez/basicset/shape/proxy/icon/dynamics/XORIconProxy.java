package org.wirez.basicset.shape.proxy.icon.dynamics;

import org.wirez.basicset.definition.icon.dynamics.XORIcon;

public final class XORIconProxy 
        extends AbstractDynamicIconProxy<XORIcon>
        implements org.wirez.shapes.proxy.icon.dynamics.XORIconProxy<XORIcon> {
    
    @Override
    public String getGlyphBackgroundColor( final XORIcon definition ) {
        return XORIcon.COLOR;
    }

    @Override
    public String getDescription( final XORIcon definition ) {
        return XORIcon.description;
    }
    
}
