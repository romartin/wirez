package org.wirez.core.client.canvas.command.factory;

import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Element;
import org.wirez.core.api.graph.Graph;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.content.ParentChildRelationship;
import org.wirez.core.client.canvas.command.impl.*;
import org.wirez.core.client.factory.ShapeFactory;

public interface CanvasCommandFactory {
    
    /* ******************************************************************************************
                                    Atomic commands.
       ****************************************************************************************** */
    
    AddCanvasNodeCommand addCanvasNodeCommand(Node candidate, ShapeFactory factory);
    
    AddCanvasEdgeCommand addCanvasEdgeCommand(Node parent, Edge candidate, ShapeFactory factory);
    
    DeleteCanvasNodeCommand deleteCanvasNodeCommand(Node candidate, ShapeFactory factory);
    
    DeleteCanvasEdgeCommand deleteCanvasEdgeCommand(Edge candidate, ShapeFactory factory);
    
    ClearCanvasCommand clearCanvasCommand();
    
    SetCanvasElementParentCommand setCanvasElementParentCommand(Node parent, 
                                                                Node candidate,
                                                                Edge<ParentChildRelationship, Node> edge);
    
    UpdateCanvasElementPositionCommand updateCanvasElementPositionCommand(Element element,
                                                                          Double x,
                                                                          Double y);
    
    UpdateCanvasElementPropertiesCommand updateCanvasElementPropertiesCommand(Element element,
                                                                              String propertyId,
                                                                              Object value);
    
    
    /* ******************************************************************************************
                                    Composite commands.
       ****************************************************************************************** */
    
    AddCanvasChildNodeCommand addCanvasChildNodeCommand(Node parent, Node candidate, ShapeFactory factory);
    
}
