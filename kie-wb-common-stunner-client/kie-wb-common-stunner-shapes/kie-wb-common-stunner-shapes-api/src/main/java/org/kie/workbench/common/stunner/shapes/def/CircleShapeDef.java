package org.kie.workbench.common.stunner.shapes.def;

public interface CircleShapeDef<W> extends BasicShapeWithTitleDef<W> {
    
    double getRadius(W element);
    
}
