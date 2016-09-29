package org.kie.workbench.common.stunner.basicset.shape.proxy.icon.statics;

import org.kie.workbench.common.stunner.basicset.definition.icon.statics.StaticIcon;
import org.kie.workbench.common.stunner.shapes.proxy.AbstractBasicShapeProxy;
import org.kie.workbench.common.stunner.shapes.proxy.icon.statics.IconProxy;
import org.kie.workbench.common.stunner.shapes.proxy.icon.statics.Icons;

public class StaticIconProxy
        extends AbstractBasicShapeProxy<StaticIcon>
        implements IconProxy<StaticIcon> {

    private static final String COLOR = "#000000";
    private static final String DESCRIPTION = "An icon";
    
    @Override
    public Icons getIcon( final StaticIcon element ) {
        return element.getIcon();
    }

    @Override
    public String getGlyphBackgroundColor( final StaticIcon element ) {
        return COLOR;
    }

    @Override
    public String getGlyphDescription(final StaticIcon element ) {
        return DESCRIPTION;
    }
    
}
