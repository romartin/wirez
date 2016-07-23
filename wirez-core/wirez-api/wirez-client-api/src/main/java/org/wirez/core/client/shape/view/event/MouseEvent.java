package org.wirez.core.client.shape.view.event;

public interface MouseEvent extends ViewEvent {
    
    double getMouseX();

    double getMouseY();

    double getClientX();

    double getClientY();

    double getScreenX();

    double getScreenY();

}
