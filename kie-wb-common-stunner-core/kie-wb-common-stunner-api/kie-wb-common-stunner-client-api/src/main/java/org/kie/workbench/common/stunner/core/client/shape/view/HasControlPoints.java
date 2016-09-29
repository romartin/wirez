package org.kie.workbench.common.stunner.core.client.shape.view;

public interface HasControlPoints<T> {

    enum ControlPointType {
        RESIZE, MAGNET;
    }

    T showControlPoints( ControlPointType type );

    T hideControlPoints();

}
