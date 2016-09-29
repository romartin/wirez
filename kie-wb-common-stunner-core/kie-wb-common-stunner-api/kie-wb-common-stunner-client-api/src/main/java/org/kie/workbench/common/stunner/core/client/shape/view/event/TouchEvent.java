package org.kie.workbench.common.stunner.core.client.shape.view.event;

public interface TouchEvent extends ViewEvent {
    
    double getTouchX();

    double getTouchY();

    boolean isShiftKeyDown();

}
