package org.wirez.core.client.canvas.event.keyboard;

import org.jboss.errai.common.client.api.annotations.NonPortable;

@NonPortable
public final class KeyUpEvent extends AbstractKeyboardEvent {

    public KeyUpEvent( Key key ) {
        super( key );
    }

}
