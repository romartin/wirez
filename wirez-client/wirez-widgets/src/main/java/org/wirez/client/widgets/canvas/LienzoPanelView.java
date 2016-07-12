package org.wirez.client.widgets.canvas;

import com.google.gwt.core.client.GWT;

public class LienzoPanelView extends FocusableLienzoPanelView implements LienzoPanel.View {

    private LienzoPanel presenter;

    public LienzoPanelView( final int width,
                            final int height ) {
        super( width,
                height );

        addKeyPressHandler( keyPressEvent -> {

            final int unicodeChar = keyPressEvent.getUnicodeCharCode();

            GWT.log( "PRESSED KEY = " + unicodeChar );

            presenter.onKeyPress( unicodeChar );

        } );

        addKeyDownHandler( keyDownEvent -> {

            final int unicodeChar = keyDownEvent.getNativeKeyCode();

            GWT.log( "DOWN KEY = " + unicodeChar );

            presenter.onKeyDown( unicodeChar );

        } );

        addKeyUpHandler( keyUpEvent -> {

            final int unicodeChar = keyUpEvent.getNativeKeyCode();

            GWT.log( "UP KEY = " + unicodeChar );

            presenter.onKeyUp( unicodeChar );
        } );

    }

    @Override
    public void init( final LienzoPanel presenter ) {
        this.presenter = presenter;
    }

}
