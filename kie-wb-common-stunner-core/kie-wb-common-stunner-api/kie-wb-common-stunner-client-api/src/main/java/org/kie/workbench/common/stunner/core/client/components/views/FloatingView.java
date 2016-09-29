package org.kie.workbench.common.stunner.core.client.components.views;

public interface FloatingView<V> {

    void add( V item );

    FloatingView<V> setX( double x );

    FloatingView<V> setY( double y );

    FloatingView<V> show();

    FloatingView<V> hide();

    FloatingView<V> setTimeOut( int timeOut );

    FloatingView<V> clearTimeOut();

    void clear();

    void destroy();

}
