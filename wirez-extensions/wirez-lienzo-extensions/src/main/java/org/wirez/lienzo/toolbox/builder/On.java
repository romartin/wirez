package org.wirez.lienzo.toolbox.builder;

import com.ait.lienzo.client.core.shape.Shape;
import com.ait.lienzo.shared.core.types.Direction;

public interface On {
    
    Towards on(Direction anchor);
    
    On attachTo(Shape<?> shape);
    
}
