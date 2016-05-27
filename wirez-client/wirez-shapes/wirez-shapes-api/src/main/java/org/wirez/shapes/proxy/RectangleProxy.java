package org.wirez.shapes.proxy;

public interface RectangleProxy<W> extends BasicNamedShapeProxy<W> {
    
    double getWidth(W element);

    double getHeight(W element);
    
}
