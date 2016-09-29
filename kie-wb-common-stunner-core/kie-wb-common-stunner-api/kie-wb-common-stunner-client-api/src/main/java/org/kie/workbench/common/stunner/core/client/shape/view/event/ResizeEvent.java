package org.kie.workbench.common.stunner.core.client.shape.view.event;

public final class ResizeEvent extends AbstractMouseEvent {

    private final double width;
    private final double height;

    public ResizeEvent( final double mouseX,
                        final double mouseY,
                        final double clientX,
                        final double clientY,
                        final double width,
                        final double height ) {
        super( mouseX, mouseY, clientX, clientY );
        this.width = width;
        this.height = height;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }
}
