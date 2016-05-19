package org.wirez.shapes.proxy;

import org.wirez.core.definition.shape.ShapeProxy;

public interface BasicShapeProxy<W>
    extends ShapeProxy<W>, BasicGlyphProxy {

    String getBackgroundColor(W element);
    
    String getBorderColor(W element);
    
    double getBorderSize(W element);
    
}
