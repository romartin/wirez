package org.kie.workbench.common.stunner.shapes.def;

public interface RectangleShapeDef<W> extends BasicShapeWithTitleDef<W> {
    
    double getWidth(W element);

    double getHeight(W element);
    
}
