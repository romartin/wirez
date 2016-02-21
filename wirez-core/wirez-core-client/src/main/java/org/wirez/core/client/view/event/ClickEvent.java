package org.wirez.core.client.view.event;

public class ClickEvent extends AbstractViewEvent {
    
    public ClickEvent(final double mouseX, 
                      final double mouseY) {
        super(mouseX, mouseY);
    }

    public ClickEvent(double mouseX, double mouseY, boolean isShiftKeyDown) {
        super(mouseX, mouseY, isShiftKeyDown);
    }
}
