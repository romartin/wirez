package org.kie.workbench.common.stunner.basicset.shape.def.icon.dynamics;

import org.kie.workbench.common.stunner.basicset.definition.icon.dynamics.PlusIcon;
import org.kie.workbench.common.stunner.shapes.def.icon.dynamics.PlusIconShapeDef;

public final class PlusIconShapeDefImpl
        extends AbstractDynamicIconShapeDef<PlusIcon>
        implements PlusIconShapeDef<PlusIcon> {
    
    @Override
    public String getGlyphBackgroundColor( final PlusIcon definition ) {
        return PlusIcon.COLOR;
    }

    @Override
    public String getGlyphDescription(final PlusIcon definition ) {
        return PlusIcon.description;
    }
    
}
