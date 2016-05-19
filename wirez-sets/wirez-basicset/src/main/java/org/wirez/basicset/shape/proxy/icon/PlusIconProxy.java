package org.wirez.basicset.shape.proxy.icon;

import org.wirez.basicset.definition.icon.PlusIcon;

public final class PlusIconProxy 
        extends AbstractIconProxy<PlusIcon>
        implements org.wirez.shapes.proxy.icon.PlusIconProxy<PlusIcon> {
    
    @Override
    public String getGlyphBackgroundColor() {
        return PlusIcon.COLOR;
    }

    @Override
    public String getDescription() {
        return PlusIcon.description;
    }
    
}
