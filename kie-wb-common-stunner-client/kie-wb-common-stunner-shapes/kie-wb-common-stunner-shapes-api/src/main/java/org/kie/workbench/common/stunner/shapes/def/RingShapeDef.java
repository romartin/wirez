package org.kie.workbench.common.stunner.shapes.def;

public interface RingShapeDef<W> extends BasicShapeWithTitleDef<W> {
    
    double getInnerRadius(W element);
    
    double getOuterRadius(W element);
    
}
