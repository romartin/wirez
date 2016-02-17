package org.wirez.core.api.graph.command;

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

    AddNodeCommand addNodeCommand(Graph target,
                                  Node candidate);

    AddEdgeCommand addEdgeCommand(Node target,
                                  Edge edge);


    DeleteNodeCommand deleteNodeCommand(Graph target,
                                        Node candidate);

    DeleteEdgeCommand deleteEdgeCommand(Edge<? extends ViewContent, 
            Node> edge);

    ClearGraphCommand clearGraphCommand(Graph target);

    SetParentCommand setParentCommand(Node parent, 
                                      Node candidate, 
                                      Edge<ParentChildRelationship, Node> edge);

    RemoveParentCommand removeParentCommand(Node parent, 
                                            Node candidate);

    SetConnectionSourceNodeCommand setConnectionSourceNodeCommand(Node<? extends ViewContent<?>, Edge> sourceNode,
                                                                  Edge<? extends ViewContent<?>, Node> edge,
                                                                  int magnetIndex);

    SetConnectionTargetNodeCommand setConnectionTargetNodeCommand(Node<? extends ViewContent<?>, Edge> targetNode,
                                                                  Edge<? extends ViewContent<?>, Node> edge,
                                                                  int magnetIndex);

    UpdateElementPositionCommand updateElementPositionCommand(Element element,
                                                              Double x,
                                                              Double y);

    UpdateElementPropertyValueCommand updateElementPropertyValueCommand(Element element,
                                                                        String propertyId,
                                                                        Object value);
    

    /* ******************************************************************************************
                                    Composite commands.
       ****************************************************************************************** */
    
    AddChildNodeCommand addChildNodeCommand(Graph target,
                                            Node parent,
                                            Node candidate);

    RemoveChildNodeCommand removeChildNodeCommand(Graph target,
                                                  Node oldParent,
                                                  Node candidate);
    
    RemoveChildNodesCommand removeChildNodesCommand(Graph target,
                                                    Node parent);
    
}
