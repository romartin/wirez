package org.kie.workbench.common.stunner.shapes.proxy;


public interface PolygonProxy<W> extends BasicShapeWithTitleProxy<W> {
    
    double getRadius(W element);
    
}
