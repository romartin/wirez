package org.wirez.client.widgets;

import com.ait.tooling.nativetools.client.event.HandlerRegistrationManager;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.RootPanel;
import org.wirez.lienzo.palette.HoverPalette;

import javax.enterprise.context.Dependent;

@Dependent
public class WidgetFloatingView extends FlowPanel implements FloatingView<IsWidget> {

    private double x;
    private double y;
    private boolean attached;
    private Timer timer;
    private int timeout= 800;
    protected final HandlerRegistrationManager handlerRegistrationManager = new HandlerRegistrationManager();

    public WidgetFloatingView() {
        this.attached = false;
    }

    @Override
    public void destroy() {
        detach();
    }

    @Override
    public WidgetFloatingView setX( final double x ) {
        this.x = x;
        return this;
    }

    @Override
    public WidgetFloatingView setY( final double y ) {
        this.y = y;
        return this;
    }

    @Override
    public WidgetFloatingView setTimeOut( final int timeout ) {
        this.timeout = timeout;
        return this;
    }

    @Override
    public WidgetFloatingView show() {
        attach();
        startTimeout();
        this.getElement().getStyle().setLeft( x, Style.Unit.PX );
        this.getElement().getStyle().setTop( y, Style.Unit.PX );
        this.getElement().getStyle().setDisplay( Style.Display.INLINE );
        return this;
    }

    @Override
    public  WidgetFloatingView hide() {
        stopTimeout();
        doHide();
        return this;
    }

    private void doHide() {
        this.getElement().getStyle().setDisplay( Style.Display.NONE );
    }

    private void attach() {

        if ( !attached ) {

            RootPanel.get().add( this );
            registerHoverEventHandlers();
            this.getElement().getStyle().setPosition( Style.Position.FIXED );
            this.getElement().getStyle().setZIndex( 20 );
            doHide();
            attached = true;

        }

    }

    private void detach() {

        if ( attached ) {

            handlerRegistrationManager.removeHandler();
            RootPanel.get().remove( this );
            attached = false;
        }

    }

    public void startTimeout() {

        if ( timeout > 0  &&
                ( null == timer || !timer.isRunning() ) ) {

            timer = new Timer() {
                @Override
                public void run() {
                    WidgetFloatingView.this.doHide();
                }
            };

            timer.schedule( timeout );

        }

    }

    public void stopTimeout() {

        if ( null != timer && timer.isRunning() ) {
            timer.cancel();
        }

    }

    private void registerHoverEventHandlers() {

        handlerRegistrationManager.register(

                this.addDomHandler( mouseOverEvent -> stopTimeout(), MouseOverEvent.getType() )

        );

        handlerRegistrationManager.register(

                this.addDomHandler( mouseOutEvent -> startTimeout(), MouseOutEvent.getType() )

        );

    }

}
