package org.wirez.basicset.shape.proxy.icon.dynamics;

import org.wirez.basicset.definition.icon.dynamics.PlusIcon;

public final class PlusIconProxy 
        extends AbstractDynamicIconProxy<PlusIcon>
        implements org.wirez.shapes.proxy.icon.dynamics.PlusIconProxy<PlusIcon> {
    
    @Override
    public String getGlyphBackgroundColor( final PlusIcon definition ) {
        return PlusIcon.COLOR;
    }

    @Override
    public String getGlyphDescription(final PlusIcon definition ) {
        return PlusIcon.description;
    }
    
}
