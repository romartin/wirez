package org.wirez.core.client.canvas.controls.toolbox.command;

import org.wirez.core.api.graph.Element;

public abstract class AbstractToolboxCommand implements ToolboxCommand{

    public void click( final Context context, final Element element ) {
        
    }

    public void drag( final Context context, final Element element ) {
        
    }

    public void mouseEnter( final Context context, final Element element ) {
        
    }

    public void mouseExit( final Context context, final Element element ) {
        
    }
    
    @Override
    public void execute( final Context context, 
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
