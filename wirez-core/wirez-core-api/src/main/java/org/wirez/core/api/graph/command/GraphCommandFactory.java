package org.wirez.core.api.graph.command;

import org.wirez.core.api.definition.Definition;
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Element;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.command.impl.*;
import org.wirez.core.api.graph.content.ParentChildRelationship;
import org.wirez.core.api.graph.content.ViewContent;
import org.wirez.core.api.graph.impl.DefaultGraph;

public interface GraphCommandFactory {
    
    AddChildNodeCommand addChildNodeCommand(DefaultGraph target,
                                            Node parent,
                                            Node candidate);

    RemoveChildNodeCommand removeChildNodeCommand(DefaultGraph target,
                                                  Node oldParent,
                                                  Node candidate);
    
    AddEdgeCommand addEdgeCommand(DefaultGraph target,
                                  Edge edge);
    
    AddNodeCommand addNodeCommand(DefaultGraph target,
                                  Node candidate);
    
    ClearGraphCommand clearGraphCommand(DefaultGraph target);
    
    DeleteEdgeCommand deleteEdgeCommand(DefaultGraph<? extends Definition, Node, Edge> graph,
                                        Edge<? extends ViewContent, Node> edge);
    
    DeleteNodeCommand deleteNodeCommand(DefaultGraph target,
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
    
    SetParentCommand setParentCommand(Node parent, Node candidate, Edge<ParentChildRelationship, Node> edge);
    
    RemoveParentCommand removeParentCommand(Node parent, Node candidate);
}
