package org.wirez.client.shapes.proxy.icon;

import org.wirez.client.shapes.proxy.BasicShapeProxy;

public interface IconProxy<W>
    extends BasicShapeProxy<W> {
    
    double getWidth(W element);

    double getHeight(W element);
    
}
