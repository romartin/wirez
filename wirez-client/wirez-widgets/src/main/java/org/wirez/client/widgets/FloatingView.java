package org.wirez.client.widgets;

public interface FloatingView<V> {

    void add( V item );

    FloatingView<V> setX( double x );

    FloatingView<V> setY( double y );

    FloatingView<V> show();

    FloatingView<V> hide();

    FloatingView<V> setTimeOut( int timeOut );

    void clear();

    void destroy();

}
