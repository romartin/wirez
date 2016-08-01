package org.wirez.core.client.canvas.event.keyboard;

public interface KeyboardEvent {

    enum Key {
        ESC( 27 );

        private final int unicharCode;

        Key( int unicharCode ) {
            this.unicharCode = unicharCode;
        }

        public int getUnicharCode() {
            return unicharCode;
        }
    }

    Key getKey();

}
