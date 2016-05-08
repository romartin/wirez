package org.wirez.client.shapes.proxy;

import org.wirez.core.client.shape.proxy.ShapeProxy;

public interface BasicShapeProxy<W>
    extends ShapeProxy<W>, BasicGlyphProxy {

    String getBackgroundColor(W element);
    
    String getBorderColor(W element);
    
    double getBorderSize(W element);
    
    String getFontFamily(W element);
    
    String getFontColor(W element);
    
    double getFontSize(W element);
    
    double getFontBorderSize(W element);
    
}
