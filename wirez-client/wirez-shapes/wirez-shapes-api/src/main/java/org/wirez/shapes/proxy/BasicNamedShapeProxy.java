package org.wirez.shapes.proxy;

public interface BasicNamedShapeProxy<W>
    extends BasicDynamicShapeProxy<W> {

    String getNamePropertyValue(W element);

    String getFontFamily(W element);
    
    String getFontColor(W element);
    
    double getFontSize(W element);
    
    double getFontBorderSize(W element);
    
}
