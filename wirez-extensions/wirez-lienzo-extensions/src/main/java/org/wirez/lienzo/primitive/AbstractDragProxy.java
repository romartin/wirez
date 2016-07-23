package org.wirez.lienzo.primitive;

import com.ait.lienzo.client.core.shape.Layer;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.RootPanel;

public abstract class AbstractDragProxy<T> {

    public interface Callback {

        void onStart(int x, int y);
        
        void onMove(int x, int y);

        void onComplete(int x, int y);

    }
    
    private boolean attached = false;
    private Timer timer;
    private Runnable timeoutRunnable;
    private Integer xDiff = null;
    private Integer yDiff = null;
    private Layer layer = null;
    private T shapeProxy = null;
    
    protected abstract void addToLayer( Layer layer, T shape );

    protected abstract void removeFromLayer( Layer layer, T shape );

    protected abstract void setX(  T shape, int x );

    protected abstract void setY( T shape, int y );
    
    public AbstractDragProxy(final Layer layer,
                             final T shape,
                             final int x,
                             final int y,
                             final int timeout,
                             final Callback callback) {
        
        this.timer = new Timer() {
            @Override
            public void run() {
                
                if ( null != timeoutRunnable ) {
                    timeoutRunnable.run();;
                }
                
            }
        };
        
        this.timer.schedule( timeout );
        this.xDiff = null;
        this.yDiff = null;
        this.layer = layer;
        this.shapeProxy = shape;

        create( x, y, timeout, callback );
        
    }

    private void create(final int initialX,
                        final int initialY, 
                        final int timeout, 
                        final Callback callback ) {

        if ( !attached ) {
            addToLayer( layer, shapeProxy );
            setX( shapeProxy, initialX );
            setY( shapeProxy, initialY );
            attached = true;
            callback.onStart( initialX, initialY );
        }


        final HandlerRegistration[] handlerRegs = new HandlerRegistration[ 3 ];

        handlerRegs[ 0 ] = RootPanel.get().addDomHandler(new MouseMoveHandler() {

            @Override
            public void onMouseMove( final MouseMoveEvent mouseMoveEvent ) {

                if ( attached ) {

                    if ( xDiff == null ) {
                        xDiff = initialX - mouseMoveEvent.getX();
                    }

                    if ( yDiff == null ) {
                        yDiff = initialY - mouseMoveEvent.getY();
                    }

                    final int x = getXDiff() + mouseMoveEvent.getX();
                    final int y = getYDiff() + mouseMoveEvent.getY();

                    setX( shapeProxy, x );
                    setY( shapeProxy, y );

                    layer.batch();

                    if ( !timer.isRunning() ) {

                        timer.schedule( timeout );

                    }

                    timeoutRunnable = () -> callback.onMove( x, y );  timer.schedule( timeout );

                }

            }
            
        }, MouseMoveEvent.getType() );

        handlerRegs[ 1 ] = RootPanel.get().addDomHandler( new MouseDownHandler() {

            @Override
            public void onMouseDown( final MouseDownEvent mouseDownEvent ) {

                mouseDownEvent.stopPropagation();
                mouseDownEvent.preventDefault();

            }

        }, MouseDownEvent.getType() );

        handlerRegs[ 2 ] = RootPanel.get().addDomHandler(new MouseUpHandler() {

            @Override
            public void onMouseUp( final MouseUpEvent mouseUpEvent ) {
                handlerRegs[ 0 ].removeHandler();
                handlerRegs[ 1 ].removeHandler();
                handlerRegs[ 2 ].removeHandler();

                if ( attached ) {

                    timer.cancel();

                    final int x = getXDiff() + mouseUpEvent.getX();
                    final int y = getYDiff() + mouseUpEvent.getY();

                    AbstractDragProxy.this.destroy();

                    callback.onComplete( x, y );

                }
                
            }
            
        }, MouseUpEvent.getType() );
        
    }

    public void destroy() {

        removeFromLayer( layer, shapeProxy );

        if ( null != this.timer && this.timer.isRunning()  ) {
            this.timer.cancel();
        }

        this.timer = null;
        this.attached = false;
        this.xDiff = null;
        this.yDiff = null;
        this.layer = null;
        this.shapeProxy = null;
        
    }


    private int getXDiff() {
        return null != xDiff ? xDiff : 0;
    }

    private int getYDiff() {
        return null != yDiff ? yDiff : 0;
    }

}
