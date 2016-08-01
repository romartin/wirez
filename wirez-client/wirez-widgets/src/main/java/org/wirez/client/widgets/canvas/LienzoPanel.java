package org.wirez.client.widgets.canvas;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import org.uberfire.client.mvp.UberView;
import org.wirez.core.client.canvas.event.keyboard.KeyDownEvent;
import org.wirez.core.client.canvas.event.keyboard.KeyPressEvent;
import org.wirez.core.client.canvas.event.keyboard.KeyUpEvent;
import org.wirez.core.client.canvas.event.keyboard.KeyboardEvent;

import javax.enterprise.context.Dependent;
import javax.enterprise.event.Event;
import javax.inject.Inject;

@Dependent
public class LienzoPanel implements IsWidget {

    public interface View extends UberView<LienzoPanel> {

        void destroy();

    }

    Event<KeyPressEvent> keyPressEvent;
    Event<KeyDownEvent> keyDownEvent;
    Event<KeyUpEvent> keyUpEvent;
    View view;

    private boolean listening;

    @Inject
    public LienzoPanel( final Event<KeyPressEvent> keyPressEvent,
                        final Event<KeyDownEvent> keyDownEvent,
                        final Event<KeyUpEvent> keyUpEvent) {
        this.keyPressEvent = keyPressEvent;
        this.keyDownEvent = keyDownEvent;
        this.keyUpEvent = keyUpEvent;
        this.listening = false;
    }

    @Override
    public Widget asWidget() {
        return view.asWidget();
    }

    public void show( final int width,
                      final int height,
                      final int padding ) {

        view = new LienzoPanelView( width + padding, height + padding );

        view.init( this );

    }

    public void destroy() {
        this.listening = false;
        view.destroy();
    }

    void onMouseOver() {
        this.listening = true;
    }

    void onMouseOut() {
        this.listening = false;
    }

    void onKeyPress( final int unicodeChar ) {

        if ( listening ) {

            final KeyboardEvent.Key key = getKey( unicodeChar );

            if ( null != key ) {

                keyPressEvent.fire( new KeyPressEvent( key ) );

            }

        }

    }

    void onKeyDown( final int unicodeChar ) {

        if ( listening ) {

            final KeyboardEvent.Key key = getKey( unicodeChar );

            if ( null != key ) {

                keyDownEvent.fire( new KeyDownEvent( key ) );

            }

        }

    }

    void onKeyUp( final int unicodeChar ) {

        if ( listening ) {

            final KeyboardEvent.Key key = getKey( unicodeChar );

            if ( null != key ) {

                keyUpEvent.fire( new KeyUpEvent( key ) );

            }

        }

    }

    private KeyboardEvent.Key getKey( final int unicodeChar ) {

        final KeyboardEvent.Key[] keys = KeyboardEvent.Key.values();

        for ( final KeyboardEvent.Key key : keys ) {

            final int c = key.getUnicharCode();

            if ( c == unicodeChar ) {

                return key;

            }

        }

        return null;

    }

}
