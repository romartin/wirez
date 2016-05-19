package org.wirez.shapes.proxy;

import org.wirez.core.client.shape.HasChildren;

import java.util.Map;

public interface HasChildProxies<W> {
    
    Map<BasicShapeProxy<W>, HasChildren.Layout> getChildProxies();
    
}
