package org.wirez.core.client.shape.view.event;

public interface TouchEvent extends ViewEvent {
    
    double getTouchX();

    double getTouchY();

    boolean isShiftKeyDown();

}
