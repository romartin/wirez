package org.wirez.core.api.graph.commands;

import org.wirez.core.api.definition.Definition;
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Element;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.content.ViewContent;
import org.wirez.core.api.graph.impl.DefaultGraph;

public interface GraphCommandFactory {
    
    AddChildNodeCommand addChildNodeCommand(DefaultGraph target,
                                            Node parent,
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
                                                                  Edge<? extends ViewContent<?>, Node> edge);
    
    SetConnectionTargetNodeCommand setConnectionTargetNodeCommand(Node<? extends ViewContent<?>, Edge> targetNode,
                                                                  Edge<? extends ViewContent<?>, Node> edge);
    
    UpdateElementPositionCommand updateElementPositionCommand(Element element,
                                                              Double x,
                                                              Double y);
    
    UpdateElementPropertyValueCommand updateElementPropertyValueCommand(Element element,
                                                                        String propertyId,
                                                                        Object value);
    
}
