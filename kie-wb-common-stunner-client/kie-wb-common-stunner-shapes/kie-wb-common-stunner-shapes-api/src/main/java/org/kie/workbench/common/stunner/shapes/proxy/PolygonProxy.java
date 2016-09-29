package org.kie.workbench.common.stunner.shapes.proxy;


public interface PolygonProxy<W> extends BasicNamedShapeProxy<W> {
    
    double getRadius(W element);
    
}
