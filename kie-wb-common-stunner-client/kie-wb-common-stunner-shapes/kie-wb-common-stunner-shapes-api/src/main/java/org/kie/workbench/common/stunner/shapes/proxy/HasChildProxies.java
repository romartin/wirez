package org.kie.workbench.common.stunner.shapes.proxy;

import org.kie.workbench.common.stunner.core.client.shape.HasChildren;

import java.util.Map;

public interface HasChildProxies<W> {
    
    Map<BasicShapeProxy<W>, HasChildren.Layout> getChildProxies();
    
}
