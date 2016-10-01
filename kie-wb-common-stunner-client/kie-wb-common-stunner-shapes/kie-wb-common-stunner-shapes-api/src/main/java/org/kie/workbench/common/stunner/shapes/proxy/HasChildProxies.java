package org.kie.workbench.common.stunner.shapes.proxy;

import org.kie.workbench.common.stunner.core.client.shape.HasChildren;
import org.kie.workbench.common.stunner.core.definition.shape.ShapeProxy;

import java.util.Map;

public interface HasChildProxies<W> {
    
    Map<ShapeProxy<W>, HasChildren.Layout> getChildProxies();
    
}
