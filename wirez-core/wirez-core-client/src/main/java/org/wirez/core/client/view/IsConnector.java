package org.wirez.core.client.view;

public interface IsConnector<T> {


    T connect(final ShapeView headShapeView,
                 final int _headMagnetsIndex,
                 final ShapeView tailShapeView,
                 final int _tailMagnetsIndex,
                 final boolean tailArrow,
                 final boolean headArrow);
    
}
