package org.wirez.core.client.shape.view.event;

public abstract class AbstractMouseEvent extends AbstractViewEvent 
        implements MouseEvent {

    protected final double mouseX;
    protected final double mouseY;

    public AbstractMouseEvent(final double mouseX,
                              final double mouseY) {
        this(mouseX, mouseY, false);
    }

    public AbstractMouseEvent(final double mouseX,
                              final double mouseY,
                              final boolean isShiftKeyDown) {
        super( isShiftKeyDown );
        this.mouseX = mouseX;
        this.mouseY = mouseY;
    }

    @Override
    public double getMouseX() {
        return mouseX;
    }

    @Override
    public double getMouseY() {
        return mouseY;
    }

}
