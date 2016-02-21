package org.wirez.core.api.graph.command.factory;

import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Element;
import org.wirez.core.api.graph.Graph;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.command.impl.*;
import org.wirez.core.api.graph.content.view.View;

public interface GraphCommandFactory {

    
    /* ******************************************************************************************
                                    Atomic commands.
       ****************************************************************************************** */

    AddNodeCommand ADD_NODE(Graph target,
                            Node candidate);

    AddEdgeCommand ADD_EDGE(Node target,
                            Edge edge);


    DeleteNodeCommand DELETE_NODE(Graph target,
                                  Node candidate);

    DeleteEdgeCommand DELETE_EDGE(Edge<? extends View, 
            Node> edge);

    ClearGraphCommand CLEAR_GRAPH(Graph target);

    AddChildEdgeCommand ADD_CHILD_EDGE(Node parent,
                                       Node candidate);
    
    AddParentEdgeCommand ADD_PARENT_EDGE(Node parent,
                                    Node candidate);

    DeleteChildEdgeCommand DELETE_CHILD_EDGE(Node parent,
                                             Node candidate);
    
    DeleteParentEdgeCommand DELETE_PARENT_EDGE(Node parent,
                                               Node candidate);

    SetConnectionSourceNodeCommand SET_SOURCE_NODE(Node<? extends View<?>, Edge> sourceNode,
                                                   Edge<? extends View<?>, Node> edge,
                                                   int magnetIndex);

    SetConnectionTargetNodeCommand SET_TARGET_NODE(Node<? extends View<?>, Edge> targetNode,
                                                   Edge<? extends View<?>, Node> edge,
                                                   int magnetIndex);

    UpdateElementPositionCommand UPDATE_POSITION(Element element,
                                                 Double x,
                                                 Double y);

    UpdateElementPropertyValueCommand UPDATE_PROPERTY_VALUE(Element element,
                                                            String propertyId,
                                                            Object value);
    

    /* ******************************************************************************************
                                    Composite commands.
       ****************************************************************************************** */
    
    AddChildNodeCommand ADD_CHILD_NODE(Graph target,
                                       Node parent,
                                       Node candidate);

    DeleteChildNodeCommand DELETE_CHILD_NODE(Graph target,
                                             Node oldParent,
                                             Node candidate);
    
    DeleteChildNodesCommand DELETE_CHILD_NODES(Graph target,
                                               Node parent);
    
}
