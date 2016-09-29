package org.kie.workbench.common.stunner.shapes.def;

public interface BasicShapeWithTitleDef<W>
    extends BasicShapeDef<W> {

    String getNamePropertyValue(W element);

    String getFontFamily(W element);
    
    String getFontColor(W element);
    
    double getFontSize(W element);
    
    double getFontBorderSize(W element);
    
}
