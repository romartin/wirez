package org.kie.workbench.common.stunner.shapes.def;


public interface PolygonShapeDef<W> extends BasicShapeWithTitleDef<W> {
    
    double getRadius(W element);
    
}
