package org.kie.workbench.common.stunner.core.client.shape.util;

import org.kie.workbench.common.stunner.core.client.shape.view.ShapeView;

public interface EdgeMagnetsHelper {
    
    int[] getDefaultMagnetsIndex( ShapeView<?> sourceView, ShapeView<?> targetView );
    
}
