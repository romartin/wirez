package org.wirez.core.client.view.event;

public class MouseClickEvent extends AbstractViewEvent {
    
    public MouseClickEvent(final double mouseX,
                           final double mouseY) {
        super(mouseX, mouseY);
    }

    public MouseClickEvent(double mouseX, double mouseY, boolean isShiftKeyDown) {
        super(mouseX, mouseY, isShiftKeyDown);
    }
}
