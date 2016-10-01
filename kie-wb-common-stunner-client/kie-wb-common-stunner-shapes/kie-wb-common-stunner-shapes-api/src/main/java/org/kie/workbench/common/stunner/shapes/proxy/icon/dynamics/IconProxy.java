package org.kie.workbench.common.stunner.shapes.proxy.icon.dynamics;

import org.kie.workbench.common.stunner.shapes.proxy.BasicShapeProxy;

public interface IconProxy<W>
    extends BasicShapeProxy<W> {
    
    double getWidth(W element);

    double getHeight(W element);
    
}
