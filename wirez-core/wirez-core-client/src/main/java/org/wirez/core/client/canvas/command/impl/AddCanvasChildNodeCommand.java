package org.wirez.core.client.canvas.command.impl;

import org.wirez.core.api.command.Command;
import org.wirez.core.api.graph.Graph;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.command.GraphCommandFactoryImpl;
import org.wirez.core.api.graph.command.impl.AddChildNodeCommand;
import org.wirez.core.api.rule.RuleManager;
import org.wirez.core.api.rule.RuleViolation;
import org.wirez.core.client.canvas.command.CanvasCommandFactory;
import org.wirez.core.client.canvas.command.HasGraphCommand;
import org.wirez.core.client.factory.ShapeFactory;

public class AddCanvasChildNodeCommand extends AbstractCanvasCompositeCommand {

    protected Node parent;
    protected Node candidate;
    protected ShapeFactory factory;

    public AddCanvasChildNodeCommand(final CanvasCommandFactory canvasCommandFactory, final Node parent, final Node candidate, final ShapeFactory factory) {
        super(canvasCommandFactory);
        this.parent = parent;
        this.candidate = candidate;
        this.factory = factory;
        
        initCommands();
    }
    
    private void initCommands() {
        this.addCommand( canvasCommandFactory.addCanvasNodeCommand( candidate, factory ) )
                .addCommand( canvasCommandFactory.setCanvasElementParentCommand( parent, candidate ) );
    }

}
