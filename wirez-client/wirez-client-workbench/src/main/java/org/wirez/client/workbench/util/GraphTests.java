/*
 * Copyright 2016 Red Hat, Inc. and/or its affiliates.
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *    http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.wirez.client.workbench.util;

import com.sun.org.apache.xerces.internal.impl.xs.opti.DefaultNode;
import org.wirez.basicset.api.Circle;
import org.wirez.basicset.api.Connector;
import org.wirez.basicset.api.Diagram;
import org.wirez.basicset.api.Rectangle;
import org.wirez.core.api.graph.Bounds;
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.content.ParentChildRelationship;
import org.wirez.core.api.graph.content.ViewContentImpl;
import org.wirez.core.api.graph.impl.*;
import org.wirez.core.api.graph.store.DefaultGraphEdgeStore;
import org.wirez.core.api.graph.store.DefaultGraphNodeStore;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class GraphTests {

    public static DefaultGraph connectionsTest2() {

        // Node1 (rectangle).
        Map<String, Object> parentNodeProperties = new HashMap<>();
        parentNodeProperties.put("name", "Node1");
        parentNodeProperties.put("bgColor", "#00CC00");
        parentNodeProperties.put("fontcolor", "#000000");
        parentNodeProperties.put("fontSize", (int)8);
        Set<String> parentNodeLabels = new HashSet<>();
        final Bounds parentNodeBounds =
                new DefaultBounds(
                        new DefaultBound(417d, 428d),
                        new DefaultBound(152d, 165d)
                );
        Node parentNode  = new NodeImpl<>("node1UUID", parentNodeProperties, parentNodeLabels, new ViewContentImpl<>(new Rectangle(), parentNodeBounds)); 

        // Child node 2 (circle).
        Map<String, Object> childNodeProperties2 = new HashMap<>();
        childNodeProperties2.put("name", "Child Node 2");
        childNodeProperties2.put("bgColor", "#0000CC");
        childNodeProperties2.put("fontcolor", "#000000");
        childNodeProperties2.put("radius", (int)25);
        childNodeProperties2.put("fontSize", (int)8);
        Set<String> childNodeLabels2 = new HashSet<>();
        final Bounds childNodeBounds2 =
                new DefaultBounds(
                        new DefaultBound(183d, 135d),
                        new DefaultBound(133d, 85d)
                );
        Node childNode2  = new NodeImpl<>("childNodeUUID2",childNodeProperties2, childNodeLabels2, new ViewContentImpl<>(new Circle(), childNodeBounds2));


        Map<String, Object> viewEdgeProperties = new HashMap<>();
        viewEdgeProperties.put("name", "View Edge");
        viewEdgeProperties.put("bgColor", "#000000");
        viewEdgeProperties.put("fontcolor", "#000000");
        viewEdgeProperties.put("fontSize", (int)8);
        Set<String> viewEdgeLabels = new HashSet<>();
        final Bounds viewEdgeBounds =
                new DefaultBounds(
                        new DefaultBound(183d, 135d),
                        new DefaultBound(133d, 85d)
                );
        Edge viewEdge = new EdgeImpl("viewEdge", viewEdgeProperties, viewEdgeLabels, new ViewContentImpl<>(new Connector(), viewEdgeBounds));
        parentNode.getOutEdges().add(viewEdge);
        childNode2.getInEdges().add(viewEdge);
        viewEdge.setSourceNode(parentNode);
        viewEdge.setTargetNode(childNode2);

        // Graph.
        Map<String, Object> graphProperties = new HashMap<>();
        graphProperties.put("name", "testGraph");
        Set<String> graphLabels = new HashSet<>();
        final Bounds graphBounds =
                new DefaultBounds(
                        new DefaultBound(150d, 150d),
                        new DefaultBound(100d, 100d)
                );

        DefaultGraph graph = new DefaultGraphImpl("graphUUID", graphProperties, graphLabels, new ViewContentImpl<>(new Diagram(), graphBounds), 
                new DefaultGraphNodeStore(), new DefaultGraphEdgeStore());
        graph.addNode(parentNode);
        graph.addNode(childNode2);

        return graph;

    }
    
    public static DefaultGraph basicTest() {

        // Parent node (rectangle).
        Map<String, Object> parentNodeProperties = new HashMap<>();
        parentNodeProperties.put("name", "Parent Node");
        parentNodeProperties.put("bgColor", "#00CC00");
        parentNodeProperties.put("fontcolor", "#000000");
        parentNodeProperties.put("fontSize", (int)8);
        Set<String> parentNodeLabels = new HashSet<>();
        final Bounds parentNodeBounds =
                new DefaultBounds(
                        new DefaultBound(417d, 428d),
                        new DefaultBound(152d, 165d)
                );
        Node parentNode  = new NodeImpl<>("parentNodeUUID", parentNodeProperties, parentNodeLabels, new ViewContentImpl<>(new Rectangle(), parentNodeBounds));

        // Child node (circle).
        Map<String, Object> childNodeProperties = new HashMap<>();
        childNodeProperties.put("name", "Child Node");
        childNodeProperties.put("bgColor", "#0000CC");
        childNodeProperties.put("fontcolor", "#000000");
        childNodeProperties.put("radius", (int)25);
        childNodeProperties.put("fontSize", (int)8);
        Set<String> childNodeLabels = new HashSet<>();
        final Bounds childNodeBounds =
                new DefaultBounds(
                        new DefaultBound(283d, 235d),
                        new DefaultBound(233d, 185d)
                );
        Node childNode  = new NodeImpl<>("childNodeUUID", childNodeProperties, childNodeLabels, new ViewContentImpl<>(new Circle(), childNodeBounds));
        
        // Graph.
        Map<String, Object> graphProperties = new HashMap<>();
        graphProperties.put("name", "testGraph");
        Set<String> graphLabels = new HashSet<>();
        final Bounds graphBounds =
                new DefaultBounds(
                        new DefaultBound(150d, 150d),
                        new DefaultBound(100d, 100d)
                );

        DefaultGraph graph = new DefaultGraphImpl("graphUUID", graphProperties, graphLabels, new ViewContentImpl<>(new Diagram(), graphBounds),
                new DefaultGraphNodeStore(), new DefaultGraphEdgeStore());
        graph.addNode(parentNode);
        graph.addNode(childNode);

        return graph;

    }
    
    public static DefaultGraph childrenTest() {

        // Parent node (rectangle).
        Map<String, Object> parentNodeProperties = new HashMap<>();
        parentNodeProperties.put("name", "Parent Node");
        parentNodeProperties.put("bgColor", "#00CC00");
        parentNodeProperties.put("fontcolor", "#000000");
        parentNodeProperties.put("fontSize", (int)8);
        Set<String> parentNodeLabels = new HashSet<>();
        final Bounds parentNodeBounds =
                new DefaultBounds(
                        new DefaultBound(417d, 428d),
                        new DefaultBound(152d, 165d)
                );
        Node parentNode  = new NodeImpl<>("parentNodeUUID", parentNodeProperties, parentNodeLabels, new ViewContentImpl<>(new Rectangle(), parentNodeBounds));

        Map<String, Object> childRelationshipProperties = new HashMap<>();
        Set<String> childRelationshipLabels = new HashSet<>();
        Edge childRelationship = new EdgeImpl("childRelationshipUUID", childRelationshipProperties, childRelationshipLabels, new ParentChildRelationship());
        parentNode.getOutEdges().add(childRelationship);
        childRelationship.setSourceNode(parentNode);
        
        
        // Child node (circle).
        Map<String, Object> childNodeProperties = new HashMap<>();
        childNodeProperties.put("name", "Child Node");
        childNodeProperties.put("bgColor", "#0000CC");
        childNodeProperties.put("fontcolor", "#000000");
        childNodeProperties.put("radius", (int)25);
        childNodeProperties.put("fontSize", (int)8);
        Set<String> childNodeLabels = new HashSet<>();
        final Bounds childNodeBounds =
                new DefaultBounds(
                        new DefaultBound(283d, 235d),
                        new DefaultBound(233d, 185d)
                );
        Node childNode  = new NodeImpl<>("childNodeUUID", childNodeProperties, childNodeLabels, new ViewContentImpl<>(new Circle(), childNodeBounds));
        childNode.getInEdges().add(childRelationship);
        childRelationship.setTargetNode(childNode);

        // Graph.
        Map<String, Object> graphProperties = new HashMap<>();
        graphProperties.put("name", "testGraph");
        Set<String> graphLabels = new HashSet<>();
        final Bounds graphBounds =
                new DefaultBounds(
                        new DefaultBound(150d, 150d),
                        new DefaultBound(100d, 100d)
                );
        
        DefaultGraph graph = new DefaultGraphImpl("graphUUID", graphProperties, graphLabels, new ViewContentImpl<>(new Diagram(), graphBounds),
                new DefaultGraphNodeStore(), new DefaultGraphEdgeStore());
        graph.addNode(parentNode);
        graph.addNode(childNode);
        
        return graph;
        
    }



    public static DefaultGraph childrenTest2() {

        // Parent node (rectangle).
        Map<String, Object> parentNodeProperties = new HashMap<>();
        parentNodeProperties.put("name", "Parent Node");
        parentNodeProperties.put("bgColor", "#00CC00");
        parentNodeProperties.put("fontcolor", "#000000");
        parentNodeProperties.put("fontSize", (int)8);
        Set<String> parentNodeLabels = new HashSet<>();
        final Bounds parentNodeBounds =
                new DefaultBounds(
                        new DefaultBound(417d, 428d),
                        new DefaultBound(152d, 165d)
                );
        Node parentNode  = new NodeImpl<>("parentNodeUUID", parentNodeProperties, parentNodeLabels, new ViewContentImpl<>(new Rectangle(), parentNodeBounds));

        Map<String, Object> childRelationshipProperties = new HashMap<>();
        Set<String> childRelationshipLabels = new HashSet<>();
        Edge childRelationship = new EdgeImpl("childRelationshipUUID", childRelationshipProperties, childRelationshipLabels, new ParentChildRelationship());
        parentNode.getOutEdges().add(childRelationship);
        childRelationship.setSourceNode(parentNode);


        // Child node 1 (circle).
        Map<String, Object> childNodeProperties = new HashMap<>();
        childNodeProperties.put("name", "Child Node");
        childNodeProperties.put("bgColor", "#0000CC");
        childNodeProperties.put("fontcolor", "#000000");
        childNodeProperties.put("radius", (int)25);
        childNodeProperties.put("fontSize", (int)8);
        Set<String> childNodeLabels = new HashSet<>();
        final Bounds childNodeBounds =
                new DefaultBounds(
                        new DefaultBound(283d, 235d),
                        new DefaultBound(233d, 185d)
                );
        Node childNode  = new NodeImpl<>("childNodeUUID", childNodeProperties, childNodeLabels, new ViewContentImpl<>(new Circle(), childNodeBounds));
        childNode.getInEdges().add(childRelationship);
        childRelationship.setTargetNode(childNode);

        Map<String, Object> childRelationshipProperties1 = new HashMap<>();
        Set<String> childRelationshipLabels1 = new HashSet<>();
        Edge childRelationship1 = new EdgeImpl("childRelationshipUUID1", childRelationshipProperties1, childRelationshipLabels1, new ParentChildRelationship());
        parentNode.getOutEdges().add(childRelationship1);
        childRelationship1.setSourceNode(parentNode);

        // Child node 2 (circle).
        Map<String, Object> childNodeProperties2 = new HashMap<>();
        childNodeProperties2.put("name", "Child Node 2");
        childNodeProperties2.put("bgColor", "#0000CC");
        childNodeProperties2.put("fontcolor", "#000000");
        childNodeProperties2.put("radius", (int)25);
        childNodeProperties2.put("fontSize", (int)8);
        Set<String> childNodeLabels2 = new HashSet<>();
        final Bounds childNodeBounds2 =
                new DefaultBounds(
                        new DefaultBound(183d, 135d),
                        new DefaultBound(133d, 85d)
                );
        Node childNode2  = new NodeImpl<>("childNodeUUID2", childNodeProperties2, childNodeLabels2, new ViewContentImpl<>(new Circle(), childNodeBounds2));
        childNode2.getInEdges().add(childRelationship1);
        childRelationship1.setTargetNode(childNode2);



        Map<String, Object> viewEdgeProperties = new HashMap<>();
        viewEdgeProperties.put("name", "View Edge");
        viewEdgeProperties.put("bgColor", "#000000");
        viewEdgeProperties.put("fontcolor", "#000000");
        viewEdgeProperties.put("fontSize", (int)8);
        Set<String> viewEdgeLabels = new HashSet<>();
        final Bounds viewEdgeBounds =
                new DefaultBounds(
                        new DefaultBound(183d, 135d),
                        new DefaultBound(133d, 85d)
                );
        Edge viewEdge = new EdgeImpl("viewEdge", viewEdgeProperties, viewEdgeLabels, new ViewContentImpl<>(new Connector(), viewEdgeBounds));
        childNode.getOutEdges().add(viewEdge);
        childNode2.getInEdges().add(viewEdge);
        viewEdge.setSourceNode(childNode);
        viewEdge.setTargetNode(childNode2);
        
        // Graph.
        Map<String, Object> graphProperties = new HashMap<>();
        graphProperties.put("name", "testGraph");
        Set<String> graphLabels = new HashSet<>();
        final Bounds graphBounds =
                new DefaultBounds(
                        new DefaultBound(150d, 150d),
                        new DefaultBound(100d, 100d)
                );

        DefaultGraph graph = new DefaultGraphImpl("graphUUID", graphProperties, graphLabels, new ViewContentImpl<>(new Diagram(), graphBounds),
                new DefaultGraphNodeStore(), new DefaultGraphEdgeStore());
        graph.addNode(parentNode);
        graph.addNode(childNode);
        graph.addNode(childNode2);

        return graph;

    }
    
    
    
}
