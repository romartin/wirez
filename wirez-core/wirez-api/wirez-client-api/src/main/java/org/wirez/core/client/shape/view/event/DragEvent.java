package org.wirez.core.client.shape.view.event;

public final class DragEvent extends AbstractMouseEvent {

    public DragEvent( final double mouseX,
                      final double mouseY,
                      final double clientX,
                      final double clientY,
                      final double screenX,
                      final double screenY ) {
        super( mouseX, mouseY, clientX, clientY, screenX, screenY  );
    }

}
