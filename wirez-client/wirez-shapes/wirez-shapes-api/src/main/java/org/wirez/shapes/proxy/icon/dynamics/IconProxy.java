package org.wirez.shapes.proxy.icon.dynamics;

import org.wirez.shapes.proxy.BasicDynamicShapeProxy;

public interface IconProxy<W>
    extends BasicDynamicShapeProxy<W> {
    
    double getWidth(W element);

    double getHeight(W element);
    
}
