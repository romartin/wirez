package org.kie.workbench.common.stunner.basicset.shape.def.icon.dynamics;

import org.kie.workbench.common.stunner.basicset.definition.icon.dynamics.XORIcon;
import org.kie.workbench.common.stunner.shapes.def.icon.dynamics.XORIconShapeDef;

public final class XORIconShapeDefImpl
        extends AbstractDynamicIconShapeDef<XORIcon>
        implements XORIconShapeDef<XORIcon> {
    
    @Override
    public String getGlyphBackgroundColor( final XORIcon definition ) {
        return XORIcon.COLOR;
    }

    @Override
    public String getGlyphDescription(final XORIcon definition ) {
        return XORIcon.description;
    }
    
}
