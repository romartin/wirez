package org.kie.workbench.common.stunner.shapes.proxy.icon.statics;

import org.kie.workbench.common.stunner.shapes.proxy.BasicShapeProxy;

public interface IconProxy<W>
        extends BasicShapeProxy<W> {
    
    Icons getIcon( W element );
    
}
