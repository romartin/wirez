package org.wirez.core.graph.command.factory;

import org.wirez.core.graph.Edge;
import org.wirez.core.graph.Element;
import org.wirez.core.graph.Graph;
import org.wirez.core.graph.Node;
import org.wirez.core.graph.command.impl.*;
import org.wirez.core.graph.content.definition.Definition;
import org.wirez.core.graph.content.view.View;

public interface GraphCommandFactory {

    AddNodeCommand ADD_NODE(Graph target,
                            Node candidate);

    AddChildNodeCommand ADD_CHILD_NODE(Graph target,
                                       Node parent,
                                       Node candidate);

    AddDockedNodeCommand ADD_DOCKED_NODE(Graph target,
                                       Node parent,
                                       Node candidate);

    AddEdgeCommand ADD_EDGE(Node target,
                            Edge edge);

    SafeDeleteNodeCommand SAFE_DELETE_NODE(Graph target,
                                           Node candidate);
    
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

    AddDockEdgeCommand ADD_DOCK_EDGE(Node parent,
                                         Node candidate);

    DeleteDockEdgeCommand DELETE_DOCK_EDGE(Node parent,
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
    
    MorphNodeCommand MORPH_NODE( Node<Definition, Edge> candidate,
                                 String morphTarget );
    
}
