package org.kie.workbench.common.stunner.core.client.canvas.event.keyboard;

public abstract class AbstractKeyboardEvent implements KeyboardEvent {

    private final KeyboardEvent.Key key;

    public AbstractKeyboardEvent( final KeyboardEvent.Key key ) {
        this.key = key;
    }

    @Override
    public Key getKey() {
        return key;
    }

}
