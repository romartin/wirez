package org.kie.workbench.common.stunner.client.lienzo.shape.util;

import com.ait.lienzo.client.core.shape.wires.WiresShape;
import org.kie.workbench.common.stunner.client.lienzo.util.LienzoShapeUtils;
import org.kie.workbench.common.stunner.core.client.shape.util.EdgeMagnetsHelper;
import org.kie.workbench.common.stunner.core.client.shape.view.ShapeView;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class LienzoMagnetsHelper implements EdgeMagnetsHelper {
    
    @Override
    public int[] getDefaultMagnetsIndex( final ShapeView<?> sourceView, 
                                       final ShapeView<?> targetView ) {
        return LienzoShapeUtils.getDefaultMagnetsIndex( (WiresShape ) sourceView, (WiresShape ) targetView );
    }
}
