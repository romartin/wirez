package org.kie.workbench.common.stunner.basicset.shape.def.icon.statics;

import org.kie.workbench.common.stunner.basicset.definition.icon.statics.StaticIcon;
import org.kie.workbench.common.stunner.core.definition.shape.AbstractShapeDef;
import org.kie.workbench.common.stunner.shapes.def.icon.statics.IconShapeDef;
import org.kie.workbench.common.stunner.shapes.def.icon.statics.Icons;

public class StaticIconShapeDefImpl
        extends AbstractShapeDef<StaticIcon>
        implements IconShapeDef<StaticIcon> {

    private static final String COLOR = "#000000";
    private static final String DESCRIPTION = "An icon";
    
    @Override
    public Icons getIcon( final StaticIcon element ) {
        return element.getIcon();
    }

    @Override
    public String getGlyphDescription(final StaticIcon element ) {
        return DESCRIPTION;
    }
    
}
