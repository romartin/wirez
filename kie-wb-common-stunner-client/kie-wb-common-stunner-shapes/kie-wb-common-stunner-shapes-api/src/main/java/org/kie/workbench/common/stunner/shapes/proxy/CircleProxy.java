package org.kie.workbench.common.stunner.shapes.proxy;

public interface CircleProxy<W> extends BasicNamedShapeProxy<W> {
    
    double getRadius(W element);
    
}
