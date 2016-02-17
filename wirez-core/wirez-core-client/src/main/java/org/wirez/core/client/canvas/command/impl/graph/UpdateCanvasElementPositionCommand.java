package org.wirez.core.client.canvas.command.impl.graph;

import org.wirez.core.api.command.Command;
import org.wirez.core.api.graph.Element;
import org.wirez.core.api.graph.Graph;
import org.wirez.core.api.graph.command.GraphCommandFactory;
import org.wirez.core.api.graph.command.GraphCommandFactoryImpl;
import org.wirez.core.api.graph.command.impl.ClearGraphCommand;
import org.wirez.core.api.graph.command.impl.UpdateElementPositionCommand;
import org.wirez.core.api.rule.RuleManager;
import org.wirez.core.api.rule.RuleViolation;
import org.wirez.core.client.canvas.command.CanvasCommandFactory;
import org.wirez.core.client.canvas.command.HasGraphCommand;

public class UpdateCanvasElementPositionCommand extends org.wirez.core.client.canvas.command.impl.UpdateCanvasElementPositionCommand implements HasGraphCommand {

    private GraphCommandFactory graphCommandFactory = new GraphCommandFactoryImpl();

    Double x;
    Double y;
    
    public UpdateCanvasElementPositionCommand(final CanvasCommandFactory canvasCommandFactory, 
                                              final Element element,
                                              final Double x,
                                              final Double y) {
        super(canvasCommandFactory, element);
        this.x = x;
        this.y = y;
    }

    @Override
    public Command<RuleManager, RuleViolation> getGraphCommand() {
        return graphCommandFactory.updateElementPositionCommand( element, x, y );
    }
    
}
