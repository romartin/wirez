package org.kie.workbench.common.stunner.core.client.canvas.controls.toolbox.command;

import com.google.gwt.user.client.Timer;
import org.kie.workbench.common.stunner.core.client.canvas.AbstractCanvasHandler;
import org.kie.workbench.common.stunner.core.client.canvas.event.selection.CanvasElementSelectedEvent;
import org.kie.workbench.common.stunner.core.client.components.toolbox.ToolboxButton;
import org.kie.workbench.common.stunner.core.graph.Element;

import javax.enterprise.event.Event;

public abstract class AbstractToolboxCommand<I> implements ToolboxCommand<AbstractCanvasHandler, I> {

    public void click( final Context<AbstractCanvasHandler> context, final Element element ) {
        
    }

    public void mouseDown( final Context<AbstractCanvasHandler> context, final Element element ) {

    }

    public void mouseEnter( final Context<AbstractCanvasHandler> context, final Element element ) {
        
    }

    public void mouseExit( final Context<AbstractCanvasHandler> context, final Element element ) {
        
    }

    @Override
    public ToolboxButton.HoverAnimation getButtonAnimation() {
        return ToolboxButton.HoverAnimation.ELASTIC;
    }

    @Override
    public void execute( final Context<AbstractCanvasHandler> context, 
                         final Element element ) {
        
        final Context.Event event = context.getEvent();
        switch (event) {
            
            case CLICK:
                click( context, element );
                break;

            case MOUSE_DOWN:
                mouseDown( context, element );
                break;

            case MOUSE_ENTER:
                mouseEnter( context, element );
                break;

            case MOUSE_EXIT:
                mouseExit( context, element );
                break;

        }
        
    }

    // Enable layer events handlers again.
    // TODO: This is a work around. If enabling canvas handlers just here ( without using the timer )
    //       the layer receives a click event, so it fires a clear selection event and it results
    //       on the element just added not being selected.
    protected void fireElementSelectedEvent( final Event<CanvasElementSelectedEvent> elementSelectedEvent,
                                             final AbstractCanvasHandler canvasHandler,
                                             final String uuid ) {

        canvasHandler.getCanvas().getLayer().disableHandlers();

        elementSelectedEvent.fire( new CanvasElementSelectedEvent( canvasHandler, uuid ) );

        final Timer t = new Timer() {

            @Override
            public void run() {

                canvasHandler.getCanvas().getLayer().enableHandlers();

            }
        };

        t.schedule( 500 );

    }

    protected void fireLoadingStarted( final Context<AbstractCanvasHandler> context ) {
        context.getCanvasHandler().getCanvas().loadingStarted();
    }

    protected void fireLoadingCompleted( final Context<AbstractCanvasHandler> context ) {
        context.getCanvasHandler().getCanvas().loadingCompleted();
    }

    
}
