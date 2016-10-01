package org.kie.workbench.common.stunner.shapes.proxy;

public interface BasicShapeWithTitleProxy<W>
    extends BasicShapeProxy<W> {

    String getNamePropertyValue(W element);

    String getFontFamily(W element);
    
    String getFontColor(W element);
    
    double getFontSize(W element);
    
    double getFontBorderSize(W element);
    
}
