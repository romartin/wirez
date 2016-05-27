package org.wirez.shapes.proxy;


public interface PolygonProxy<W> extends BasicNamedShapeProxy<W> {
    
    double getRadius(W element);
    
}
