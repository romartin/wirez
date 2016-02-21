package org.wirez.core.client.view.event;

public class AbstractViewEvent implements ViewEvent {

    protected final double mouseX;
    protected final double mouseY;
    protected final boolean isShiftKeyDown;

    public AbstractViewEvent(final double mouseX, 
                             final double mouseY) {
        this(mouseX, mouseY, false);
    }

    public AbstractViewEvent(final double mouseX, 
                             final double mouseY, 
                             final boolean isShiftKeyDown) {
        this.mouseX = mouseX;
        this.mouseY = mouseY;
        this.isShiftKeyDown = isShiftKeyDown;
    }

    @Override
    public double getMouseX() {
        return mouseX;
    }

    @Override
    public double getMouseY() {
        return mouseY;
    }

    @Override
    public boolean isShiftKeyDown() {
        return isShiftKeyDown;
    }
}
