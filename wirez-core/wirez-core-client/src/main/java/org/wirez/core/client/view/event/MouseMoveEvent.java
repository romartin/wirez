package org.wirez.core.client.view.event;

public class MouseMoveEvent extends AbstractViewEvent {
    
    public MouseMoveEvent(final double mouseX,
                          final double mouseY) {
        super(mouseX, mouseY);
    }

    public MouseMoveEvent(double mouseX, double mouseY, boolean isShiftKeyDown) {
        super(mouseX, mouseY, isShiftKeyDown);
    }
}
