package org.wirez.shapes.proxy.icon.statics;

import org.wirez.shapes.proxy.BasicShapeProxy;

public interface IconProxy<W>
        extends BasicShapeProxy<W> {
    
    Icons getIcon( W element );
    
}
