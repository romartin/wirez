package org.wirez.core.client.control.toolbox.command;

import com.ait.lienzo.client.core.shape.Shape;
import com.google.gwt.user.client.Window;
import org.wirez.core.api.graph.Element;
import org.wirez.core.api.graph.Node;
import org.wirez.core.client.canvas.command.factory.CanvasCommandFactory;
import org.wirez.core.client.util.SVGUtils;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class RemoveToolboxCommand implements ToolboxCommand {

    CanvasCommandFactory commandFactory;
    
    private final Shape<?> icon;

    @Inject
    public RemoveToolboxCommand(final CanvasCommandFactory commandFactory) {
        this.commandFactory = commandFactory;
        this.icon = (Shape<?>) SVGUtils.createSVGIcon(SVGUtils.getRemove());;
    }

    @Override
    public Shape<?> getIcon() {
        return icon;
    }

    @Override
    public String getTitle() {
        return "Remove";
    }

    @Override
    public void execute(final Context context, 
                        final Element element) {
        
        // TODO: Remove use of hardcoded confirm box here.
        if ( Window.confirm("Are you sure?") ) {
            context.getCanvasHandler().execute( commandFactory.DELETE_NODE((Node) element) );
        }
        
    }
    
}
