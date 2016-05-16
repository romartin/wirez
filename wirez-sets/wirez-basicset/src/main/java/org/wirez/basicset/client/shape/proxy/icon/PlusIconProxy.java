package org.wirez.basicset.client.shape.proxy.icon;

import org.wirez.basicset.api.icon.PlusIcon;

public final class PlusIconProxy 
        extends AbstractIconProxy<PlusIcon>
        implements org.wirez.client.shapes.proxy.icon.PlusIconProxy<PlusIcon> {
    
    @Override
    public String getGlyphBackgroundColor() {
        return PlusIcon.COLOR;
    }

    @Override
    public String getDescription() {
        return PlusIcon.description;
    }
    
}
