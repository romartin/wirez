package org.wirez.core.client.shape.util;

import org.wirez.core.client.shape.view.ShapeView;

public interface EdgeMagnetsHelper {
    
    int[] getDefaultMagnetsIndex( ShapeView<?> sourceView, ShapeView<?> targetView );
    
}
