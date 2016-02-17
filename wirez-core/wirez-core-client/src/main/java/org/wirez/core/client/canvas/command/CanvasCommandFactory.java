package org.wirez.core.client.canvas.command;

import org.wirez.core.api.graph.Graph;
import org.wirez.core.api.graph.Node;
import org.wirez.core.client.canvas.command.impl.*;
import org.wirez.core.client.factory.ShapeFactory;

public interface CanvasCommandFactory {
    
    /* ******************************************************************************************
                                    Atomic commands.
       ****************************************************************************************** */
    
    AddCanvasNodeCommand addCanvasNodeCommand(Node candidate, ShapeFactory factory);
    
    AddCanvasEdgeCommand addCanvasEdgeCommand();
    
    DeleteCanvasNodeCommand deleteCanvasNodeCommand();
    
    DeleteCanvasEdgeCommand deleteCanvasEdgeCommand();
    
    ClearCanvasCommand clearCanvasCommand();
    
    SetCanvasElementParentCommand setCanvasElementParentCommand(Node parent, Node candidate);
    
    UpdateCanvasElementPositionCommand updateCanvasElementPositionCommand();
    
    UpdateCanvasElementPropertiesCommand updateCanvasElementPropertiesCommand();
    
    
    /* ******************************************************************************************
                                    Composite commands.
       ****************************************************************************************** */
    
    AddCanvasChildNodeCommand addCanvasChildNodeCommand();
    
    
}
