package org.wirez.lienzo.primitive;

import com.ait.lienzo.client.core.shape.Layer;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
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
        
        create( layer, shape, x, y, timeout, callback );
        
    }

    private void create(final Layer layer, 
                        final T copy, 
                        final int initialX, 
                        final int initialY, 
                        final int timeout, 
                        final Callback callback ) {
        final HandlerRegistration[] handlerRegs = new HandlerRegistration[ 2 ];

        handlerRegs[ 0 ] = RootPanel.get().addDomHandler(new MouseMoveHandler() {

            @Override
            public void onMouseMove( final MouseMoveEvent mouseMoveEvent ) {
                
                if ( xDiff == null ) {
                    xDiff = initialX - mouseMoveEvent.getX();
                }

                if ( yDiff == null ) {
                    yDiff = initialY - mouseMoveEvent.getY();
                }
                
                if ( timer.isRunning() ) {
                    timer.cancel();
                }
                
                timer.schedule( timeout );
                
                final int x = xDiff + mouseMoveEvent.getX();
                final int y = yDiff + mouseMoveEvent.getY();

                if ( !attached ) {
                    addToLayer( layer, copy );
                    attached = true;
                    callback.onStart( x, y );
                }
                
                setX( copy, x );
                setY( copy, y );

                layer.batch();
                
                timeoutRunnable = () -> callback.onMove( x, y );
                
            }
            
        }, MouseMoveEvent.getType() );

        handlerRegs[ 1 ] = RootPanel.get().addDomHandler(new MouseUpHandler() {

            @Override
            public void onMouseUp( final MouseUpEvent mouseUpEvent ) {
                handlerRegs[ 0 ].removeHandler();
                handlerRegs[ 1 ].removeHandler();

                if ( attached ) {

                    timer.cancel();

                    final int x = xDiff + mouseUpEvent.getX();
                    final int y = yDiff + mouseUpEvent.getY();

                    removeFromLayer( layer, copy );
                    
                    callback.onComplete( x, y );
                    
                }
                
                
            }
            
        }, MouseUpEvent.getType() );
    }
    
    
}
