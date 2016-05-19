package org.wirez.core.client.canvas.controls.toolbox.command;

import org.wirez.core.graph.Element;
import org.wirez.core.client.canvas.AbstractCanvasHandler;

public abstract class AbstractToolboxCommand<I> implements ToolboxCommand<AbstractCanvasHandler, I> {

    public void click( final Context<AbstractCanvasHandler> context, final Element element ) {
        
    }

    public void drag( final Context<AbstractCanvasHandler> context, final Element element ) {
        
    }

    public void mouseEnter( final Context<AbstractCanvasHandler> context, final Element element ) {
        
    }

    public void mouseExit( final Context<AbstractCanvasHandler> context, final Element element ) {
        
    }
    
    @Override
    public void execute( final Context<AbstractCanvasHandler> context, 
                         final Element element ) {
        
        final Context.Event event = context.getEvent();
        switch (event) {
            
            case CLICK:
                click( context, element );
                break;

            case DRAG:
                drag( context, element );
                break;

            case MOUSE_ENTER:
                mouseEnter( context, element );
                break;

            case MOUSE_EXIT:
                mouseExit( context, element );
                break;

        }
        
    }
    
}
