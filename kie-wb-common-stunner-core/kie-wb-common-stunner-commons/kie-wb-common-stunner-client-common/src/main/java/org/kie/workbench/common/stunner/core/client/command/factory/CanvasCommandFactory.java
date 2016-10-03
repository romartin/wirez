package org.kie.workbench.common.stunner.core.client.command.factory;

import org.kie.workbench.common.stunner.core.client.command.impl.*;
import org.kie.workbench.common.stunner.core.client.shape.factory.ShapeFactory;
import org.kie.workbench.common.stunner.core.definition.morph.MorphDefinition;
import org.kie.workbench.common.stunner.core.graph.Edge;
import org.kie.workbench.common.stunner.core.graph.Element;
import org.kie.workbench.common.stunner.core.graph.Node;
import org.kie.workbench.common.stunner.core.graph.content.definition.Definition;
import org.kie.workbench.common.stunner.core.graph.content.view.View;

public interface CanvasCommandFactory {
    
    AddCanvasNodeCommand ADD_NODE( Node candidate, ShapeFactory factory);
    
    AddCanvasEdgeCommand ADD_EDGE(Node parent, Edge candidate, ShapeFactory factory);
    
    DeleteCanvasNodeCommand DELETE_NODE( Node candidate);
    
    DeleteCanvasEdgeCommand DELETE_EDGE(Edge candidate);

    DrawCanvasCommand DRAW();

    ClearCanvasCommand CLEAR_CANVAS();
    
    AddCanvasChildEdgeCommand ADD_CHILD_EDGE( Node parent,
                                              Node candidate);
    
    DeleteCanvasChildEdgeCommand DELETE_CHILD_EDGE(Node parent,
                                                   Node candidate);
    
    AddCanvasParentEdgeCommand ADD_PARENT_EDGE(Node parent,
                                               Node candidate);
    
    DeleteCanvasParentEdgeCommand DELETE_PARENT_EDGE(Node parent,
                                                     Node candidate);

    AddCanvasDockEdgeCommand ADD_DOCK_EDGE(Node parent,
                                               Node candidate);

    DeleteCanvasDockEdgeCommand DELETE_DOCK_EDGE(Node parent,
                                                     Node candidate);

    UpdateCanvasElementPositionCommand UPDATE_POSITION(Element element,
                                                       Double x,
                                                       Double y);
    
    UpdateCanvasElementPropertyCommand UPDATE_PROPERTY(Element element,
                                                       String propertyId,
                                                       Object value);

    UpdateCanvasElementPropertiesCommand UPDATE_PROPERTIES(Element element);
    
    AddCanvasChildNodeCommand ADD_CHILD_NODE(Node parent, Node candidate, ShapeFactory factory);

    AddCanvasDockedNodeCommand ADD_DOCKED_NODE(Node parent, Node candidate, ShapeFactory factory);
    
    SetCanvasConnectionSourceNodeCommand SET_SOURCE_NODE(Node<? extends View<?>, Edge> node,
                                                         Edge<? extends View<?>, Node> edge,
                                                         int magnetIndex);
    
    SetCanvasConnectionTargetNodeCommand SET_TARGET_NODE(Node<? extends View<?>, Edge> node,
                                                         Edge<? extends View<?>, Node> edge,
                                                         int magnetIndex);

    MorphCanvasNodeCommand MORPH_NODE( Node<? extends Definition<?>, Edge> candidate,
                                       MorphDefinition morphDefinition,
                                       String morphTarget,
                                       ShapeFactory factory );
    
}
