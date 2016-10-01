package org.kie.workbench.common.stunner.shapes.proxy.icon.statics;

import org.kie.workbench.common.stunner.core.definition.shape.ShapeProxy;

public interface IconProxy<W>
        extends ShapeProxy<W> {
    
    Icons getIcon( W element );
    
}
