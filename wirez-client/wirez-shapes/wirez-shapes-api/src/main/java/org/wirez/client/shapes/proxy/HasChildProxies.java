package org.wirez.client.shapes.proxy;

import org.wirez.client.shapes.HasChildren;

import java.util.Map;

public interface HasChildProxies<W> {
    
    Map<BasicShapeProxy<W>, HasChildren.Layout> getChildProxies();
    
}
