package org.wirez.core.client.shape.view;

public interface IsConnector<T> {


    T connect(final org.wirez.core.client.shape.view.ShapeView headShapeView,
              final int _headMagnetsIndex,
              final ShapeView tailShapeView,
              final int _tailMagnetsIndex,
              final boolean tailArrow,
              final boolean headArrow);
    
}
