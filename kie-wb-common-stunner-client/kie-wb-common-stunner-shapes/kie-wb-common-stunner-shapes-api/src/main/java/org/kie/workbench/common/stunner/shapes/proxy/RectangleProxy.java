package org.kie.workbench.common.stunner.shapes.proxy;

public interface RectangleProxy<W> extends BasicShapeWithTitleProxy<W> {
    
    double getWidth(W element);

    double getHeight(W element);
    
}
