package org.wirez.core.client.canvas.controls.toolbox.command;

import com.google.gwt.user.client.Window;
import org.wirez.core.graph.Element;
import org.wirez.core.graph.Node;
import org.wirez.core.client.canvas.AbstractCanvasHandler;
import org.wirez.core.client.canvas.command.CanvasCommandManager;
import org.wirez.core.client.canvas.command.factory.CanvasCommandFactory;

import javax.inject.Inject;

public abstract class RemoveToolboxCommand<I> extends AbstractToolboxCommand<I> {

    CanvasCommandFactory commandFactory;
    CanvasCommandManager<AbstractCanvasHandler> canvasCommandManager;
    
    private final I icon;

    @Inject
    public RemoveToolboxCommand(final CanvasCommandFactory commandFactory,
                                final CanvasCommandManager<AbstractCanvasHandler> canvasCommandManager,
                                final I icon) {
        this.commandFactory = commandFactory;
        this.canvasCommandManager = canvasCommandManager;
        this.icon = icon;
    }

    @Override
    public I getIcon() {
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
