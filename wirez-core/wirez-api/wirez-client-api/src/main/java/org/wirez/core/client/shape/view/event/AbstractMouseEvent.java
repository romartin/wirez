package org.wirez.core.client.shape.view.event;

public abstract class AbstractMouseEvent extends AbstractViewEvent
        implements MouseEvent {

    protected final double mouseX;
    protected final double mouseY;
    protected final double clientX;
    protected final double clientY;
    protected final double screenX;
    protected final double screenY;

    public AbstractMouseEvent( final double mouseX,
                               final double mouseY,
                               final double clientX,
                               final double clientY,
                               final double screenX,
                               final double screenY ) {
        this( mouseX, mouseY, clientX, clientY, screenX, screenY, false );
    }

    public AbstractMouseEvent( final double mouseX,
                               final double mouseY,
                               final double clientX,
                               final double clientY,
                               final double screenX,
                               final double screenY,
                               final boolean isShiftKeyDown ) {
        setShiftKeyDown( isShiftKeyDown );
        this.mouseX = mouseX;
        this.mouseY = mouseY;
        this.clientX = clientX;
        this.clientY = clientY;
        this.screenX = screenX;
        this.screenY = screenY;
    }

    @Override
    public double getMouseX() {
        return mouseX;
    }

    @Override
    public double getMouseY() {
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

    @Override
    public double getScreenX() {
        return screenX;
    }

    @Override
    public double getScreenY() {
        return screenY;
    }

}
