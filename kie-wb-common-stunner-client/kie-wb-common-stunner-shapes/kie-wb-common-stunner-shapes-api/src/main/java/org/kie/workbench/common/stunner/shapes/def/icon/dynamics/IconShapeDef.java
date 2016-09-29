package org.kie.workbench.common.stunner.shapes.def.icon.dynamics;

import org.kie.workbench.common.stunner.shapes.def.BasicShapeDef;

public interface IconShapeDef<W>
    extends BasicShapeDef<W> {
    
    double getWidth(W element);

    double getHeight(W element);
    
}
