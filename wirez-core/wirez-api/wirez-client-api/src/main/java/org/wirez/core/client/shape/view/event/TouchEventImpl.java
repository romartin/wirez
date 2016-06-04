package org.wirez.core.client.shape.view.event;

public final class TouchEventImpl extends AbstractViewEvent implements TouchEvent {

    private final double x;
    private final double y;
    private final double touchX;
    private final double touchY;
    
    public TouchEventImpl(final double x,
                          final double y,
                          final double touchX,
                          final double touchY) {
        
        super( false );
        this.touchX = touchX;
        this.touchY = touchY;
        this.x = x;
        this.y = y;
    }

    public double getTouchX() {
        return touchX;
    }

    public double getTouchY() {
        return touchY;
    }
    
}
