package org.wirez.core.client.shape.view.event;

public final class MouseMoveEvent extends AbstractMouseEvent {

    public MouseMoveEvent( final double mouseX,
                           final double mouseY,
                           final double clientX,
                           final double clientY,
                           final double screenX,
                           final double screenY ) {
        super( mouseX, mouseY, clientX, clientY, screenX, screenY );
    }

    public MouseMoveEvent( final double mouseX,
                           final double mouseY,
                           final double clientX,
                           final double clientY,
                           final double screenX,
                           final double screenY,
                           boolean isShiftKeyDown ) {
        super( mouseX, mouseY, clientX, clientY, screenX, screenY, isShiftKeyDown );
    }
}
