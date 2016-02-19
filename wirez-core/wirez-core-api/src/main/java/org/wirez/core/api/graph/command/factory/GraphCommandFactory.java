package org.wirez.core.api.graph.command.factory;

import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Element;
import org.wirez.core.api.graph.Graph;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.command.impl.*;
import org.wirez.core.api.graph.content.ParentChildRelationship;
import org.wirez.core.api.graph.content.ViewContent;

public interface GraphCommandFactory {

    
    /* ******************************************************************************************
                                    Atomic commands.
       ****************************************************************************************** */

    AddNodeCommand ADD_NOE(Graph target,
                           Node candidate);

    AddEdgeCommand ADD_EDGE(Node target,
                            Edge edge);


    DeleteNodeCommand DELETE_NODE(Graph target,
                                  Node candidate);

    DeleteEdgeCommand DELETE_EDGE(Edge<? extends ViewContent, 
            Node> edge);

    ClearGraphCommand CLEAR_GRAPH(Graph target);

    AddChildCommand ADD_CHILD(Node parent,
                              Node candidate);
    
    SetParentCommand SET_PARENT(Node parent,
                                Node candidate,
                                Edge<ParentChildRelationship, Node> edge);

    DeleteParentCommand DELETE_PARENT(Node parent,
                                      Node candidate);

    SetConnectionSourceNodeCommand SET_SOURCE_NODE(Node<? extends ViewContent<?>, Edge> sourceNode,
                                                   Edge<? extends ViewContent<?>, Node> edge,
                                                   int magnetIndex);

    SetConnectionTargetNodeCommand SET_TARGET_NODE(Node<? extends ViewContent<?>, Edge> targetNode,
                                                   Edge<? extends ViewContent<?>, Node> edge,
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
