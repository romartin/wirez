package org.wirez.lienzo.palette;

import com.ait.lienzo.client.core.shape.IPrimitive;
import com.ait.lienzo.client.core.shape.Rectangle;
import com.google.gwt.user.client.Timer;
import org.wirez.lienzo.grid.Grid;

public class HoverMiniPalette extends AbstractMiniPalette<HoverMiniPalette> {

    public interface CloseCallback {
        
        void onClose();
        
    }

    private Timer timer;
    private CloseCallback closeCallback;
    private int timeout= 800;

    public HoverMiniPalette setCloseCallback( final CloseCallback callback ) {
        this.closeCallback = callback;
        return this;
    }

    public HoverMiniPalette setTimeout( final int timeout ) {
        this.timeout = timeout;
        return this;
    }

    @Override
    protected Rectangle addPaletteDecorator( final Grid grid ) {
        
        final Rectangle decorator = super.addPaletteDecorator(grid);

        decorator.addNodeMouseEnterHandler(event -> stopTimeout() );
        
        decorator.addNodeMouseExitHandler(event -> startTimeout() );
        
        return decorator;
    }

    @Override
    protected void doShowItem(final int index,
                              final double x,
                              final double y) {
        super.doShowItem(index, x, y);
        stopTimeout();
    }

    @Override
    protected void doItemOut(final int index) {
        super.doItemOut(index);
        startTimeout();
    }

    @Override
    public HoverMiniPalette build(final IPrimitive<?>... items) {
        HoverMiniPalette result = super.build(items);
        startTimeout();
        return result;
    }

    @Override
    public HoverMiniPalette clear() {
        HoverMiniPalette result = super.clear();
        stopTimeout();
        return result;
    }

    private void startTimeout() {
        
        if ( null == timer || !timer.isRunning() ) {

            timer = new Timer() {
                @Override
                public void run() {
                    if ( null != HoverMiniPalette.this.callback ) {
                        HoverMiniPalette.this.closeCallback.onClose();
                    }
                }
            };

            timer.schedule( timeout );
            
        }
        
    }

    private void stopTimeout() {
        
        if ( null != timer && timer.isRunning() ) {
            timer.cancel();
        }
        
    }
    
}
