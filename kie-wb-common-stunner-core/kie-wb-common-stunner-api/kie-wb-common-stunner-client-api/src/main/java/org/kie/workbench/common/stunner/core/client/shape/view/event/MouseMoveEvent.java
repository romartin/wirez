package org.kie.workbench.common.stunner.core.client.shape.view.event;

public final class MouseMoveEvent extends AbstractMouseEvent {

    public MouseMoveEvent( final double mouseX,
                           final double mouseY,
                           final double clientX,
                           final double clientY ) {
        super( mouseX, mouseY, clientX, clientY );
    }

    public MouseMoveEvent( final double mouseX,
                           final double mouseY,
                           final double clientX,
                           final double clientY,
                           boolean isShiftKeyDown ) {
        super( mouseX, mouseY, clientX, clientY, isShiftKeyDown );
    }
}
