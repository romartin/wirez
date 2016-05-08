package org.wirez.client.shapes.proxy;

public interface RectangleProxy<W> extends BasicShapeProxy<W> {
    
    double getWidth(W element);

    double getHeight(W element);
    
}
