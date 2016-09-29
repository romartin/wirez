package org.kie.workbench.common.stunner.basicset.shape.proxy.icon.dynamics;

import org.kie.workbench.common.stunner.basicset.definition.icon.dynamics.XORIcon;

public final class XORIconProxy 
        extends AbstractDynamicIconProxy<XORIcon>
        implements org.kie.workbench.common.stunner.shapes.proxy.icon.dynamics.XORIconProxy<XORIcon> {
    
    @Override
    public String getGlyphBackgroundColor( final XORIcon definition ) {
        return XORIcon.COLOR;
    }

    @Override
    public String getGlyphDescription(final XORIcon definition ) {
        return XORIcon.description;
    }
    
}
