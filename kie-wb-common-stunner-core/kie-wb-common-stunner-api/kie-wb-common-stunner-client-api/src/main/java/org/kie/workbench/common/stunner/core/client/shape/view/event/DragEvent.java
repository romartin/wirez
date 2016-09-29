package org.kie.workbench.common.stunner.core.client.shape.view.event;

public final class DragEvent extends AbstractMouseEvent {

    private final int dx;
    private final int dy;

    public DragEvent( final double mouseX,
                      final double mouseY,
                      final double clientX,
                      final double clientY,
                      final int dx,
                      final int dy) {
        super( mouseX, mouseY, clientX, clientY );
        this.dx = dx;
        this.dy = dy;
    }

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }

}
