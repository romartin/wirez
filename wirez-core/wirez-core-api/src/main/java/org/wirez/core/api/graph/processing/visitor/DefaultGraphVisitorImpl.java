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

package org.wirez.core.api.graph.processing.visitor;

import org.wirez.core.api.DefinitionManager;
import org.wirez.core.api.adapter.PropertyAdapter;
import org.wirez.core.api.definition.property.Property;
import org.wirez.core.api.graph.*;
import org.wirez.core.api.graph.content.ParentChildRelationship;
import org.wirez.core.api.graph.content.ViewContent;
import org.wirez.core.api.graph.impl.*;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.*;

@Dependent
public class DefaultGraphVisitorImpl implements DefaultGraphVisitor {

    @Inject
    DefinitionManager definitionManager;
    
    private DefaultGraph graph;
    private DefaultGraphVisitorCallback visitorCallback;
    private GraphVisitorPolicy policy;
    private DefaultGraphVisitorCallback.BoundsVisitorCallback boundsVisitor = null;
    private DefaultGraphVisitorCallback.PropertyVisitorCallback propertyVisitor = null;
    private Set<String> processesEdges;

    /*
        ******************************************
        *   PUBLIC API
        ******************************************
     */
    
    @Override
    public DefaultGraphVisitorImpl setBoundsVisitorCallback(DefaultGraphVisitorCallback.BoundsVisitorCallback callback) {
        this.boundsVisitor = callback;
        return this;
    }

    @Override
    public DefaultGraphVisitorImpl setPropertiesVisitorCallback(DefaultGraphVisitorCallback.PropertyVisitorCallback callback) {
        this.propertyVisitor = callback;
        return this;
    }

    // For quick development and testing... remove later
    public DefaultGraphVisitorImpl setDefinitionManager(DefinitionManager definitionManager) {
        this.definitionManager = definitionManager;
        return this;
    }

    @Override
    public void visit(DefaultGraph graph, DefaultGraphVisitorCallback callback, GraphVisitorPolicy policy) {
        this.graph = graph;
        this.visitorCallback = callback;
        this.policy = policy;
        this.processesEdges = new HashSet<String>();
        visitGraph();
        visitUnconnectedEdges();
        visitorCallback.endVisit();
    }
    
     /*
        ******************************************
        *   PRIVATE METHODS
        ******************************************
     */
    
    private void visitGraph() {
        assert graph != null && visitorCallback != null;

        
        final Object content = graph.getContent();
        if (content instanceof ViewContent) {
            visitorCallback.visitGraphWithViewContent(graph);
            visitBounds(graph);
        } else {
            visitorCallback.visitGraph(graph);
        }
        visitProperties(graph);
        ;
        Collection<Node> startingNodes = getStartingNodes(graph);
        if (!startingNodes.isEmpty()) {
            for (Node node : startingNodes) {
                visitNode(node);
            }
        }
    }

    private void visitNode(final Node graphNode) {

        final Object contet = graphNode.getContent();
        
        if (contet instanceof ViewContent) {
            visitorCallback.visitNodeWithViewContent(graphNode);
            visitBounds(graphNode);
        } else {
            visitorCallback.visitNode(graphNode);
        }
        visitProperties(graphNode);
        
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
            if (GraphVisitorPolicy.EDGE_FIRST.equals(policy)) {
                doVisitEdge(edge);
            }
            final Node outNode = (Node) edge.getTargetNode();
            if (outNode != null) {
                visitNode(outNode);
            }
            if (GraphVisitorPolicy.EDGE_LAST.equals(policy)) {
                doVisitEdge(edge);
            }
        }

    }

    private void doVisitEdge(final Edge edge) {
        
        final Object content = edge.getContent();
        if (content instanceof ViewContent) {
            visitorCallback.visitEdgeWithViewContent(edge);
            visitBounds(edge);
        } else if (content instanceof ParentChildRelationship) {
            visitorCallback.visitEdgeWithParentChildRelationContent(edge);
        } else {
            visitorCallback.visitEdge(edge);
        }
        visitProperties(edge);
        
    }
    
    private void visitUnconnectedEdges() {
        Iterable<Edge> edges = graph.edges();
        Iterator<Edge> edgesIt = edges.iterator();
        while (edgesIt.hasNext()) {
            Edge edge = edgesIt.next();
            if (!this.processesEdges.contains(edge.getUUID())) {
                doVisitEdge(edge);
            }
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
        if (element != null && propertyVisitor != null && definitionManager != null) {
            final Set<Property> properties = element.getProperties();
            if (properties != null) {
                for (final Property property : properties) {
                    PropertyAdapter adapter = definitionManager.getPropertyAdapter(property);
                    if ( null != adapter ) {
                        final Object value = adapter.getValue(property);
                        propertyVisitor.visitProperty(element, property.getId(), value);
                    } 
                }
            }
        }
    }

    private void visitBounds(final Element<? extends ViewContent> element) {
        if (element != null && boundsVisitor != null) {
            final Bounds bounds = element.getContent().getBounds();
            if (bounds != null) {
                boundsVisitor.visitBounds(element, bounds.getUpperLeft(), bounds.getLowerRight());
            }
        }
    }

    
}
