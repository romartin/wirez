package org.wirez.basicset.client.shape.proxy.icon;

import org.wirez.basicset.api.icon.XORIcon;

public final class XORIconProxy 
        extends AbstractIconProxy<XORIcon>
        implements org.wirez.client.shapes.proxy.icon.XORIconProxy<XORIcon> {
    
    @Override
    public String getGlyphBackgroundColor() {
        return XORIcon.COLOR;
    }

    @Override
    public String getDescription() {
        return XORIcon.description;
    }
    
}
