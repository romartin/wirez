package org.wirez.shapes.proxy;

public interface RingProxy<W> extends BasicNamedShapeProxy<W> {
    
    double getInnerRadius(W element);
    
    double getOuterRadius(W element);
    
}
