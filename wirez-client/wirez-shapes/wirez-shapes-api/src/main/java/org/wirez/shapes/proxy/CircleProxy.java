package org.wirez.shapes.proxy;

public interface CircleProxy<W> extends BasicNamedShapeProxy<W> {
    
    double getRadius(W element);
    
}
