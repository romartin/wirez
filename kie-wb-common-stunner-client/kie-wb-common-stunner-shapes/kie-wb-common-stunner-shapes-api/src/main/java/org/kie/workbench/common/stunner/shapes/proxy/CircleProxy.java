package org.kie.workbench.common.stunner.shapes.proxy;

public interface CircleProxy<W> extends BasicShapeWithTitleProxy<W> {
    
    double getRadius(W element);
    
}
