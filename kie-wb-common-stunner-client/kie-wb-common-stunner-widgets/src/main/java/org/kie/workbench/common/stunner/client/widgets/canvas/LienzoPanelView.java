package org.kie.workbench.common.stunner.client.widgets.canvas;

import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.user.client.ui.RootPanel;
import org.kie.workbench.common.stunner.core.client.shape.view.event.HandlerRegistrationImpl;

public class LienzoPanelView extends FocusableLienzoPanelView implements LienzoPanel.View {

    private LienzoPanel presenter;
    private final HandlerRegistrationImpl handlerRegistrationManager = new HandlerRegistrationImpl();

    public LienzoPanelView( final int width,
                            final int height ) {
        super( width,
                height );


        handlerRegistrationManager.register(

                addMouseOverHandler( mouseOverEvent -> presenter.onMouseOver() )

        );

        handlerRegistrationManager.register(

                addMouseOutHandler( mouseOutEvent -> presenter.onMouseOut() )

        );

        handlerRegistrationManager.register(

                RootPanel.get().addDomHandler( keyPressEvent -> {

                    final int unicodeChar = keyPressEvent.getUnicodeCharCode();

                    presenter.onKeyPress( unicodeChar );

                }, KeyPressEvent.getType() )

        );

        handlerRegistrationManager.register(
                RootPanel.get().addDomHandler( keyDownEvent -> {

                    final int unicodeChar = keyDownEvent.getNativeKeyCode();

                    presenter.onKeyDown( unicodeChar );

                }, KeyDownEvent.getType() )
        );

        handlerRegistrationManager.register(
                RootPanel.get().addDomHandler( keyUpEvent -> {

                    final int unicodeChar = keyUpEvent.getNativeKeyCode();

                    presenter.onKeyUp( unicodeChar );

                }, KeyUpEvent.getType() )
        );


    }

    @Override
    public void init( final LienzoPanel presenter ) {
        this.presenter = presenter;
    }

    @Override
    public void destroy() {
        handlerRegistrationManager.removeHandler();
        presenter = null;
    }

}
