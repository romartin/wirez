package org.wirez.shapes.proxy;

public interface BasicTitledShapeProxy<W>
    extends BasicShapeProxy<W> {

    String getFontFamily(W element);
    
    String getFontColor(W element);
    
    double getFontSize(W element);
    
    double getFontBorderSize(W element);
    
}
