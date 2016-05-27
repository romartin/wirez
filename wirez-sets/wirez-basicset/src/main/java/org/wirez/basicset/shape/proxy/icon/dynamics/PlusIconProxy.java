package org.wirez.basicset.shape.proxy.icon.dynamics;

import org.wirez.basicset.definition.icon.dynamics.PlusIcon;

public final class PlusIconProxy 
        extends AbstractDynamicIconProxy<PlusIcon>
        implements org.wirez.shapes.proxy.icon.dynamics.PlusIconProxy<PlusIcon> {
    
    @Override
    public String getGlyphBackgroundColor() {
        return PlusIcon.COLOR;
    }

    @Override
    public String getDescription() {
        return PlusIcon.description;
    }
    
}
