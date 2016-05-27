package org.wirez.shapes.proxy;

public interface BasicDynamicShapeProxy<W>
    extends BasicShapeProxy<W> {

    String getBackgroundColor(W element);
    
    String getBorderColor(W element);
    
    double getBorderSize(W element);
    
}
