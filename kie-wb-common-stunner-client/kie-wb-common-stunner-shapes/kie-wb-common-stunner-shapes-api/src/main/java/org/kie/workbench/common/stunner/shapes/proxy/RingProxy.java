package org.kie.workbench.common.stunner.shapes.proxy;

public interface RingProxy<W> extends BasicShapeWithTitleProxy<W> {
    
    double getInnerRadius(W element);
    
    double getOuterRadius(W element);
    
}
