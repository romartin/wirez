package org.wirez.basicset.shape.proxy.icon;

import org.wirez.basicset.definition.icon.XORIcon;

public final class XORIconProxy 
        extends AbstractIconProxy<XORIcon>
        implements org.wirez.shapes.proxy.icon.XORIconProxy<XORIcon> {
    
    @Override
    public String getGlyphBackgroundColor() {
        return XORIcon.COLOR;
    }

    @Override
    public String getDescription() {
        return XORIcon.description;
    }
    
}
