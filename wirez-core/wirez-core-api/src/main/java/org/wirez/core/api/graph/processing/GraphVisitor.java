/*
 * Copyright 2015 Red Hat, Inc. and/or its affiliates.
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

package org.wirez.core.api.graph.processing;

import org.wirez.core.api.graph.*;
import org.wirez.core.api.graph.impl.ChildRelationship;
import org.wirez.core.api.graph.impl.DefaultGraph;
import org.wirez.core.api.graph.impl.ViewEdge;
import org.wirez.core.api.graph.impl.ViewNode;

import java.util.*;

public class GraphVisitor {

    public enum VisitorPolicy {
        EDGE_FIRST, EDGE_LAST;
    }
    
    private DefaultGraph graph;
    private Visitor visitor;
    private VisitorPolicy policy;
    private Visitor.BoundsVisitor boundsVisitor = null;
    private Visitor.PropertyVisitor propertyVisitor = null;
    private Set<String> processesEdges;

    public GraphVisitor(final DefaultGraph graph,
                        final Visitor visitor,
                        final VisitorPolicy policy) {
        this.graph = graph;
        this.visitor = visitor;
        this.policy = policy;
        this.processesEdges = new HashSet<String>();
    }
    
    public void run() {
        processesEdges.clear();
        visitGraph();
        visitUnconnectedEdges();
        visitor.endVisit();
    }

    public GraphVisitor setBoundsVisitor(Visitor.BoundsVisitor boundsVisitor) {
        this.boundsVisitor = boundsVisitor;
        return this;
    }

    public GraphVisitor setPropertyVisitor(Visitor.PropertyVisitor propertyVisitor) {
        this.propertyVisitor = propertyVisitor;
        return this;
    }

    private void visitUnconnectedEdges() {
        Iterable<Edge> edges = graph.edges();
        Iterator<Edge> edgesIt = edges.iterator();
        while (edgesIt.hasNext()) {
            Edge edge = edgesIt.next();
            if (!this.processesEdges.contains(edge.getUUID())) {
                visitor.visitUnconnectedEdge(edge);
            }
        }
    }


    private void visitGraph() {
        assert graph != null && visitor != null;
        
        visitor.visitGraph(graph);
        visitProperties(graph);
        visitBounds(graph)
        ;
        Collection<Node> startingNodes = getStartingNodes(graph);
        if (!startingNodes.isEmpty()) {
            for (Node node : startingNodes) {
                visitNode(node);
            }
        }
    }

    private void visitNode(final Node graphNode) {
        
        if (graphNode instanceof ViewNode) {
            final ViewNode viewNode = (ViewNode) graphNode;
            visitor.visitViewNode(viewNode);
            visitProperties(viewNode);
            visitBounds(viewNode);
        } else {
            visitor.visitNode(graphNode);
            visitProperties(graphNode);
        }
        List<Edge> outEdges = graphNode.getOutEdges();
        if (outEdges != null && !outEdges.isEmpty()) {
            for (Edge edge : outEdges) {
                visitEdge(edge);
            }
        }
    }

    private void visitEdge(final Edge edge) {
        if (!this.processesEdges.contains(edge.getUUID())) {
            processesEdges.add(edge.getUUID());
            if (VisitorPolicy.EDGE_FIRST.equals(policy)) {
                doVisitEdge(edge);
            }
            final Node outNode = (Node) edge.getTargetNode();
            if (outNode != null) {
                visitNode(outNode);
            }
            if (VisitorPolicy.EDGE_LAST.equals(policy)) {
                doVisitEdge(edge);
            }
        }
        
    }
    
    private void doVisitEdge(final Edge edge) {
        if (edge instanceof ViewEdge) {
            final ViewEdge viewEdge = (ViewEdge) edge;
            visitor.visitViewEdge(viewEdge);
            visitProperties(viewEdge);
            visitBounds(viewEdge);
        } else if (edge instanceof ChildRelationship) {
            final ChildRelationship childRelationship = (ChildRelationship) edge;
            visitor.visitChildRelationship(childRelationship);
            visitProperties(childRelationship);
        }
    }

    private Collection<Node> getStartingNodes(final DefaultGraph graph) {
        final Collection<Node> result = new LinkedList<Node>();

        final Iterator<Node> nodesIt = graph.nodes().iterator();
        while (nodesIt.hasNext()) {
            final Node node = nodesIt.next();
            if (node.getInEdges() == null || !node.getInEdges().iterator().hasNext()) {
                result.add(node);
            }
        }

        return result;
    }
    
    private void visitProperties(final Element element) {
        if (element != null && propertyVisitor != null) {
            final Map<String, Object> properties = element.getProperties();
            if (properties != null) {
                for (final Map.Entry<String, Object> entry : properties.entrySet()) {
                    propertyVisitor.visitProperty(element, entry.getKey(), entry.getValue());
                }
            }
        }
    }

    private void visitBounds(final HasView element) {
        if (element != null && boundsVisitor != null) {
            final Bounds bounds = element.getBounds();
            if (bounds != null) {
                boundsVisitor.visitBounds(element, bounds.getUpperLeft(), bounds.getLowerRight());
            }
        }
    }
    
}
