package org.kie.workbench.common.stunner.shapes.def.icon.statics;

import org.kie.workbench.common.stunner.core.definition.shape.ShapeDef;

public interface IconShapeDef<W>
        extends ShapeDef<W> {
    
    Icons getIcon( W element );
    
}
