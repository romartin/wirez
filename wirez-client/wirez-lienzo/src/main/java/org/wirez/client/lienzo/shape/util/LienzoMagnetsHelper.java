package org.wirez.client.lienzo.shape.util;

import com.ait.lienzo.client.core.shape.wires.WiresShape;
import org.wirez.client.lienzo.util.LienzoShapeUtils;
import org.wirez.core.client.shape.util.EdgeMagnetsHelper;
import org.wirez.core.client.shape.view.ShapeView;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class LienzoMagnetsHelper implements EdgeMagnetsHelper {
    
    @Override
    public int[] getDefaultMagnetsIndex( final ShapeView<?> sourceView, 
                                       final ShapeView<?> targetView ) {
        return LienzoShapeUtils.getDefaultMagnetsIndex( (WiresShape ) sourceView, (WiresShape ) targetView );
    }
}
