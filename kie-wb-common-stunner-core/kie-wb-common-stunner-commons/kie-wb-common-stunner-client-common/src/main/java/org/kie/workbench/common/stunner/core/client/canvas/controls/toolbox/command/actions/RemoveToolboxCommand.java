package org.kie.workbench.common.stunner.core.client.canvas.controls.toolbox.command.actions;

import com.google.gwt.user.client.Window;
import org.kie.workbench.common.stunner.core.client.canvas.AbstractCanvasHandler;
import org.kie.workbench.common.stunner.core.client.command.CanvasCommandManager;
import org.kie.workbench.common.stunner.core.client.command.factory.CanvasCommandFactory;
import org.kie.workbench.common.stunner.core.client.canvas.controls.toolbox.command.AbstractToolboxCommand;
import org.kie.workbench.common.stunner.core.client.canvas.controls.toolbox.command.Context;
import org.kie.workbench.common.stunner.core.graph.Element;
import org.kie.workbench.common.stunner.core.graph.Node;

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
