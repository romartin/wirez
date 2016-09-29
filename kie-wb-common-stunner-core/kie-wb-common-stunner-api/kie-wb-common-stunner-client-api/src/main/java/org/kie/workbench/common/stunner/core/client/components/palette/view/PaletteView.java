package org.kie.workbench.common.stunner.core.client.components.palette.view;

public interface PaletteView<T, L, I extends PaletteElementView> {

    T setX( double x );

    T setY( double y );

    double getX();

    double getY();

    double getWidth();

    double getHeight();

    T add( I item );

    T set( int pos, I item );

    T remove( int pos );

    T attach( L layer );

    T show();

    T hide();

    T clear();

    void destroy();
    
}
