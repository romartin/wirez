package org.wirez.core.client.canvas.command.factory;

import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Element;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.content.ParentChildRelationship;
import org.wirez.core.api.graph.content.view.View;
import org.wirez.core.client.canvas.command.impl.*;
import org.wirez.core.client.factory.ShapeFactory;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CanvasCommandFactoryImpl implements CanvasCommandFactory {
    
    @Override
    public AddCanvasNodeCommand ADD_NODE(Node candidate, ShapeFactory factory) {
        return new AddCanvasNodeCommand(this, candidate, factory);
    }

    @Override
    public AddCanvasEdgeCommand ADD_EDGE(Node parent, Edge candidate, ShapeFactory factory) {
        return new AddCanvasEdgeCommand(this, parent, candidate, factory);
    }

    @Override
    public DeleteCanvasNodeCommand DELETE_NODE(Node candidate) {
        return new DeleteCanvasNodeCommand(this, candidate);
    }

    @Override
    public DeleteCanvasEdgeCommand DELETE_EDGE(Edge candidate) {
        return new DeleteCanvasEdgeCommand(this, candidate);
    }

    @Override
    public ClearCanvasCommand CLEAR_CANVAS() {
        return new ClearCanvasCommand(this);
    }

    @Override
    public AddCanvasChildCommand ADD_CHILD(final Node parent, final Node candidate) {
        return new AddCanvasChildCommand(this, parent, candidate);
    }

    @Override
    public SetCanvasElementParentCommand SET_PARENT(Node parent,
                                                    Node candidate,
                                                    Edge<ParentChildRelationship, Node> edge) {
        return new SetCanvasElementParentCommand(this, parent, candidate, edge);
    }

    @Override
    public UpdateCanvasElementPositionCommand UPDATE_POSITION(Element element,
                                                              Double x,
                                                              Double y) {
        return new UpdateCanvasElementPositionCommand(this, element, x, y);
    }

    @Override
    public UpdateCanvasElementPropertyCommand UPDATE_PROPERTY(Element element,
                                                              String propertyId,
                                                              Object value) {
        return new UpdateCanvasElementPropertyCommand(this, element, propertyId, value);
    }

    @Override
    public UpdateCanvasElementPropertiesCommand UPDATE_PROPERTIES(final Element element) {
        return new UpdateCanvasElementPropertiesCommand(this, element);
    }

    @Override
    public AddCanvasChildNodeCommand ADD_CHILD_NODE(Node parent, Node candidate, ShapeFactory factory) {
        return new AddCanvasChildNodeCommand(this, parent, candidate, factory);
    }

    @Override
    public SetCanvasConnectionSourceNodeCommand SET_SOURCE_NODE(Node<? extends View<?>, Edge> node, Edge<? extends View<?>, Node> edge, int magnetIndex) {
        return new SetCanvasConnectionSourceNodeCommand(this, node, edge, magnetIndex);
    }

    @Override
    public SetCanvasConnectionTargetNodeCommand SET_TARGET_NODE(Node<? extends View<?>, Edge> node, Edge<? extends View<?>, Node> edge, int magnetIndex) {
        return new SetCanvasConnectionTargetNodeCommand(this, node, edge, magnetIndex);
    }


}
