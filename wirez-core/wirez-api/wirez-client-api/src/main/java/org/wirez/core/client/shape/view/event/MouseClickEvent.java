package org.wirez.core.client.shape.view.event;

public final class MouseClickEvent extends AbstractMouseEvent {

    private boolean isButtonLeft;
    private boolean isButtonMiddle;
    private boolean isButtonRight;

    public MouseClickEvent( final double mouseX,
                            final double mouseY,
                            final double clientX,
                            final double clientY,
                            final double screenX,
                            final double screenY ) {
        super( mouseX, mouseY, clientX, clientY, screenX, screenY  );
    }

    public boolean isButtonLeft() {
        return isButtonLeft;
    }

    public void setButtonLeft( boolean buttonLeft ) {
        isButtonLeft = buttonLeft;
    }

    public boolean isButtonMiddle() {
        return isButtonMiddle;
    }

    public void setButtonMiddle( boolean buttonMiddle ) {
        isButtonMiddle = buttonMiddle;
    }

    public boolean isButtonRight() {
        return isButtonRight;
    }

    public void setButtonRight( boolean buttonRight ) {
        isButtonRight = buttonRight;
    }
}
