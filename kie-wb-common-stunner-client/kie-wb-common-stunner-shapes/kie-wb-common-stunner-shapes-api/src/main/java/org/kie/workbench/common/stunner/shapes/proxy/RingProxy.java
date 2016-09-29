package org.kie.workbench.common.stunner.shapes.proxy;

public interface RingProxy<W> extends BasicNamedShapeProxy<W> {
    
    double getInnerRadius(W element);
    
    double getOuterRadius(W element);
    
}
