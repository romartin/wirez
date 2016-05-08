package org.wirez.core.client.shape.proxy;

import org.wirez.core.client.shape.view.ShapeView;

public interface ShapeProxy<W>
    extends org.wirez.core.client.shape.proxy.GlyphProxy {

    String getNamePropertyValue(W element);
    
}
