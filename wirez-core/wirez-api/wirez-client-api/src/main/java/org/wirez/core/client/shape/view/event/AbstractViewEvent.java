package org.wirez.core.client.shape.view.event;

public abstract class AbstractViewEvent implements ViewEvent {

    protected final boolean isShiftKeyDown;

    public AbstractViewEvent(final boolean isShiftKeyDown) {
        this.isShiftKeyDown = isShiftKeyDown;
    }

    @Override
    public boolean isShiftKeyDown() {
        return isShiftKeyDown;
    }
    
}
