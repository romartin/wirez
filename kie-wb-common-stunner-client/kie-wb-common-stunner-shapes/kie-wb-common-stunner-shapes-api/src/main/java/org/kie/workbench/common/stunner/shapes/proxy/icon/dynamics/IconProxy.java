package org.kie.workbench.common.stunner.shapes.proxy.icon.dynamics;

import org.kie.workbench.common.stunner.shapes.proxy.BasicDynamicShapeProxy;

public interface IconProxy<W>
    extends BasicDynamicShapeProxy<W> {
    
    double getWidth(W element);

    double getHeight(W element);
    
}
