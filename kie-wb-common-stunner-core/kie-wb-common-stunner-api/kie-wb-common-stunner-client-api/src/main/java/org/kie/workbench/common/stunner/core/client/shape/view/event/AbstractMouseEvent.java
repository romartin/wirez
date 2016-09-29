package org.kie.workbench.common.stunner.core.client.shape.view.event;

public abstract class AbstractMouseEvent extends AbstractViewEvent
        implements MouseEvent {

    private final double mouseX;
    private final double mouseY;
    private final double clientX;
    private final double clientY;

    public AbstractMouseEvent( final double mouseX,
                               final double mouseY,
                               final double clientX,
                               final double clientY ) {
        this( mouseX, mouseY, clientX, clientY, false );
    }

    public AbstractMouseEvent( final double mouseX,
                               final double mouseY,
                               final double clientX,
                               final double clientY,
                               final boolean isShiftKeyDown ) {
        setShiftKeyDown( isShiftKeyDown );
        this.mouseX = mouseX;
        this.mouseY = mouseY;
        this.clientX = clientX;
        this.clientY = clientY;
    }

    @Override
    public double getX() {
        return mouseX;
    }

    @Override
    public double getY() {
        return mouseY;
    }

    @Override
    public double getClientX() {
        return clientX;
    }

    @Override
    public double getClientY() {
        return clientY;
    }

}
