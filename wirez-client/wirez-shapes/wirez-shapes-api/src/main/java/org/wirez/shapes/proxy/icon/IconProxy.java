package org.wirez.shapes.proxy.icon;

import org.wirez.shapes.proxy.BasicShapeProxy;

public interface IconProxy<W>
    extends BasicShapeProxy<W> {
    
    double getWidth(W element);

    double getHeight(W element);
    
}
