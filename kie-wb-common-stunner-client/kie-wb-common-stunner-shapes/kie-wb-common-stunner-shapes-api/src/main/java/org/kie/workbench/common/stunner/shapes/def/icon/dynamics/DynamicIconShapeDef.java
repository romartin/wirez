package org.kie.workbench.common.stunner.shapes.def.icon.dynamics;

public interface DynamicIconShapeDef<W> extends IconShapeDef<W> {
    
    Icons getIcon(W definition);
    
}
