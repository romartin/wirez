package org.wirez.client.shapes.proxy;

public interface CircleProxy<W> extends BasicTitledShapeProxy<W> {
    
    double getRadius(W element);
    
}
