package org.wirez.core.client.canvas.command.factory;

import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Element;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.content.ParentChildRelationship;
import org.wirez.core.client.canvas.command.impl.*;
import org.wirez.core.client.factory.ShapeFactory;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CanvasCommandFactoryImpl implements CanvasCommandFactory {
    
    @Override
    public AddCanvasNodeCommand addCanvasNodeCommand(Node candidate, ShapeFactory factory) {
        return new AddCanvasNodeCommand(this, candidate, factory);
    }

    @Override
    public AddCanvasEdgeCommand addCanvasEdgeCommand(Node parent, Edge candidate, ShapeFactory factory) {
        return new AddCanvasEdgeCommand(this, parent, candidate, factory);
    }

    @Override
    public DeleteCanvasNodeCommand deleteCanvasNodeCommand(Node candidate, ShapeFactory factory) {
        return new DeleteCanvasNodeCommand(this, candidate, factory);
    }

    @Override
    public DeleteCanvasEdgeCommand deleteCanvasEdgeCommand(Edge candidate, ShapeFactory factory) {
        return new DeleteCanvasEdgeCommand(this, candidate, factory);
    }

    @Override
    public ClearCanvasCommand clearCanvasCommand() {
        return new ClearCanvasCommand(this);
    }

    @Override
    public SetCanvasElementParentCommand setCanvasElementParentCommand(Node parent, 
                                                                       Node candidate,
                                                                       Edge<ParentChildRelationship, Node> edge) {
        return new SetCanvasElementParentCommand(this, parent, candidate, edge);
    }

    @Override
    public UpdateCanvasElementPositionCommand updateCanvasElementPositionCommand(Element element,
                                                                                 Double x,
                                                                                 Double y) {
        return new UpdateCanvasElementPositionCommand(this, element, x, y);
    }

    @Override
    public UpdateCanvasElementPropertiesCommand updateCanvasElementPropertiesCommand(Element element,
                                                                                     String propertyId,
                                                                                     Object value) {
        return new UpdateCanvasElementPropertiesCommand(this, element, propertyId, value);
    }

    @Override
    public AddCanvasChildNodeCommand addCanvasChildNodeCommand(Node parent, Node candidate, ShapeFactory factory) {
        return new AddCanvasChildNodeCommand(this, parent, candidate, factory);
    }


}
