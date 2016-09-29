package org.kie.workbench.common.stunner.core.client.canvas.event.keyboard;

import org.jboss.errai.common.client.api.annotations.NonPortable;

@NonPortable
public final class KeyDownEvent extends AbstractKeyboardEvent {

    public KeyDownEvent( Key key ) {
        super( key );
    }

}
