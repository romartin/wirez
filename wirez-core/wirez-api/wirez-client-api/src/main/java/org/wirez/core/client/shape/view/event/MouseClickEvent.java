package org.wirez.core.client.shape.view.event;

public final class MouseClickEvent extends AbstractMouseEvent {
    
    public MouseClickEvent(final double mouseX,
                           final double mouseY) {
        super(mouseX, mouseY);
    }

    public MouseClickEvent(double mouseX, double mouseY, boolean isShiftKeyDown) {
        super(mouseX, mouseY, isShiftKeyDown);
    }
}
