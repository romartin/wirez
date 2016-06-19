package org.wirez.core.client.canvas.controls.toolbox.command.actions;

import com.google.gwt.user.client.Window;
import org.wirez.core.client.canvas.AbstractCanvasHandler;
import org.wirez.core.client.canvas.command.CanvasCommandManager;
import org.wirez.core.client.canvas.command.factory.CanvasCommandFactory;
import org.wirez.core.client.canvas.controls.toolbox.command.AbstractToolboxCommand;
import org.wirez.core.client.canvas.controls.toolbox.command.Context;
import org.wirez.core.graph.Element;
import org.wirez.core.graph.Node;

public abstract class RemoveToolboxCommand<I> extends AbstractToolboxCommand<I> {

    CanvasCommandFactory commandFactory;
    CanvasCommandManager<AbstractCanvasHandler> canvasCommandManager;
    
    private final I icon;

    public RemoveToolboxCommand(final CanvasCommandFactory commandFactory,
                                final CanvasCommandManager<AbstractCanvasHandler> canvasCommandManager,
                                final I icon) {
        this.commandFactory = commandFactory;
        this.canvasCommandManager = canvasCommandManager;
        this.icon = icon;
    }

    @Override
    public I getIcon( final double width, final double height ) {
        return icon;
    }

    @Override
    public String getTitle() {
        return "Remove";
    }

    @Override
    public void click(final Context<AbstractCanvasHandler> context,
                        final Element element) {
        
        // TODO: Remove use of hardcoded confirm box here.
        if ( Window.confirm("Are you sure?") ) {
            canvasCommandManager.execute( context.getCanvasHandler(), commandFactory.DELETE_NODE((Node) element) );
        }
        
    }

    @Override
    public void destroy() {
        this.commandFactory = null;
        this.canvasCommandManager = null;
    }
    
}
