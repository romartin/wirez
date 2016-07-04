package org.wirez.core.client.canvas.event.keyboard;

public interface KeyboardEvent {

    enum Key {
        ESC;
    }

    Key getKey();

}
