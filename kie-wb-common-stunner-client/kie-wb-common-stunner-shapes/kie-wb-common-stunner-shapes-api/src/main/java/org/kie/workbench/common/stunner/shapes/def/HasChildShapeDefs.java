package org.kie.workbench.common.stunner.shapes.def;

import org.kie.workbench.common.stunner.core.client.shape.HasChildren;
import org.kie.workbench.common.stunner.core.definition.shape.ShapeDef;

import java.util.Map;

public interface HasChildShapeDefs<W> {
    
    Map<ShapeDef<W>, HasChildren.Layout> getChildShapeDefs();
    
}
