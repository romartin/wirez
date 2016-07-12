package org.wirez.client.widgets.canvas;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import org.uberfire.client.mvp.UberView;
import org.wirez.core.client.canvas.event.keyboard.KeyDownEvent;
import org.wirez.core.client.canvas.event.keyboard.KeyPressEvent;
import org.wirez.core.client.canvas.event.keyboard.KeyUpEvent;
import org.wirez.core.client.canvas.event.keyboard.KeyboardEvent;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.enterprise.event.Event;
import javax.inject.Inject;

@Dependent
public class LienzoPanel implements IsWidget {

    public interface View extends UberView<LienzoPanel> {

    }

    Event<KeyPressEvent> keyPressEvent;
    Event<KeyDownEvent> keyDownEvent;
    Event<KeyUpEvent> keyUpEvent;
    View view;

    @Inject
    public LienzoPanel( final Event<KeyPressEvent> keyPressEvent,
                        final Event<KeyDownEvent> keyDownEvent,
                        final Event<KeyUpEvent> keyUpEvent) {
        this.keyPressEvent = keyPressEvent;
        this.keyDownEvent = keyDownEvent;
        this.keyUpEvent = keyUpEvent;
    }

    @PostConstruct
    public void init() {
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

    public void clear() {

    }

    void onKeyPress( final int unicodeChar ) {

        final KeyboardEvent.Key key = getKey( unicodeChar );

        if ( null != key ) {

            keyPressEvent.fire( new KeyPressEvent( key ) );

        }

    }

    void onKeyDown( final int unicodeChar ) {

        final KeyboardEvent.Key key = getKey( unicodeChar );

        if ( null != key ) {

            keyDownEvent.fire( new KeyDownEvent( key ) );

        }

    }

    void onKeyUp( final int unicodeChar ) {

        final KeyboardEvent.Key key = getKey( unicodeChar );

        if ( null != key ) {

            keyUpEvent.fire( new KeyUpEvent( key ) );

        }

    }

    private KeyboardEvent.Key getKey( final int unicodeChar ) {
        // TODO
        return null;
    }

}
