package org.wirez.core.client.canvas.command.impl.graph;

import org.wirez.core.api.command.Command;
import org.wirez.core.api.graph.Element;
import org.wirez.core.api.graph.command.GraphCommandFactory;
import org.wirez.core.api.graph.command.GraphCommandFactoryImpl;
import org.wirez.core.api.graph.command.impl.UpdateElementPositionCommand;
import org.wirez.core.api.graph.command.impl.UpdateElementPropertyValueCommand;
import org.wirez.core.api.rule.RuleManager;
import org.wirez.core.api.rule.RuleViolation;
import org.wirez.core.client.canvas.command.CanvasCommandFactory;
import org.wirez.core.client.canvas.command.HasGraphCommand;

public class UpdateCanvasElementPropertyCommand extends org.wirez.core.client.canvas.command.impl.UpdateCanvasElementPropertiesCommand implements HasGraphCommand {

    private GraphCommandFactory graphCommandFactory = new GraphCommandFactoryImpl();

    String propertyId;
    Object value;
    
    public UpdateCanvasElementPropertyCommand(final CanvasCommandFactory canvasCommandFactory,
                                              final Element element,
                                              final String propertyId,
                                              final Object value) {
        super(canvasCommandFactory, element);
        this.propertyId = propertyId;
        this.value = value;
    }

    @Override
    public Command<RuleManager, RuleViolation> getGraphCommand() {
        return graphCommandFactory.updateElementPropertyValueCommand(element, propertyId, value);
    }
    
}
