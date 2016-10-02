package org.kie.workbench.common.stunner.basicset.shape.def.icon.dynamics;

import org.kie.workbench.common.stunner.basicset.definition.icon.dynamics.MinusIcon;
import org.kie.workbench.common.stunner.shapes.def.icon.dynamics.MinusIconShapeDef;

public final class MinusIconShapeDefImpl
        extends AbstractDynamicIconShapeDef<MinusIcon>
        implements MinusIconShapeDef<MinusIcon> {
    
    @Override
    public String getGlyphBackgroundColor( final MinusIcon definition ) {
        return MinusIcon.COLOR;
    }

    @Override
    public String getGlyphDescription(final MinusIcon definition ) {
        return MinusIcon.description;
    }
    
}
