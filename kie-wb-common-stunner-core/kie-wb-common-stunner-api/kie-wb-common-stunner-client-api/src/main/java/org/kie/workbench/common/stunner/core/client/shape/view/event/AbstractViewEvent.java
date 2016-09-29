package org.kie.workbench.common.stunner.core.client.shape.view.event;

public abstract class AbstractViewEvent implements ViewEvent {

    protected boolean isShiftKeyDown;
    protected boolean isAltKeyDown;
    protected boolean isMetaKeyDown;

    public AbstractViewEvent() {
        this( false, false, false );
    }

    public AbstractViewEvent( final boolean isShiftKeyDown,
                              final boolean isAltKeyDown,
                              final boolean isMetaKeyDown ) {
        this.isShiftKeyDown = isShiftKeyDown;
        this.isAltKeyDown = isAltKeyDown;
        this.isMetaKeyDown = isMetaKeyDown;
    }

    public boolean isShiftKeyDown() {
        return isShiftKeyDown;
    }

    public void setShiftKeyDown( boolean shiftKeyDown ) {
        isShiftKeyDown = shiftKeyDown;
    }

    public boolean isAltKeyDown() {
        return isAltKeyDown;
    }

    public void setAltKeyDown( boolean altKeyDown ) {
        isAltKeyDown = altKeyDown;
    }

    public boolean isMetaKeyDown() {
        return isMetaKeyDown;
    }

    public void setMetaKeyDown( boolean metaKeyDown ) {
        isMetaKeyDown = metaKeyDown;
    }

}
