package org.wirez.client.shapes.proxy;

import org.wirez.core.client.shape.proxy.ShapeProxy;

public interface BasicShapeProxy<W>
    extends ShapeProxy<W>, BasicGlyphProxy {

    String getBackgroundColor(W element);
    
    String getBorderColor(W element);
    
    double getBorderSize(W element);
    
}
