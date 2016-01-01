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

package org.wirez.core.api.graph.processing.handler;

import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Element;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.impl.*;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

@Dependent
public class DefaultGraphHandlerImpl implements DefaultGraphHandler {

    DefaultGraph graph;

    @Override
    public DefaultGraphHandlerImpl initialize(final DefaultGraph graph) {
        this.graph = graph;
        return this;
    }

    @Override
    public Element get(final String uuid) {
        assert graph != null && uuid != null;
        Element element = graph.getNode(uuid);
        if (element == null) {
            element = graph.getEdge(uuid);
        }
        return element;
    }

    @Override
    public Node getNode(final String uuid) {
        assert graph != null && uuid != null;
        if ( null != uuid ) {
            return graph.getNode(uuid);
        }
        return null;
    }

    @Override
    public DefaultNode getDefaultNode(final String uuid) {
        return (DefaultNode) getNode(uuid);
    }

    @Override
    public ViewNode getViewNode(final String uuid) {
        return (ViewNode) getNode(uuid);
    }

    @Override
    public Collection<Node> findNodes(final List<String> labels) {
        return findElements((Iterable<Node>) graph.nodes(), labels);
    }

    @Override
    public Edge getEdge(final String uuid) {
        assert graph != null && uuid != null;
        if ( null != uuid ) {
            return graph.getEdge(uuid);
        }
        return null;
    }

    @Override
    public DefaultEdge getDefaultEdge(final String uuid) {
        return (DefaultEdge) getEdge(uuid);
    }

    @Override
    public ViewEdge getViewEdge(final String uuid) {
        return (ViewEdge) getEdge(uuid);
    }

    @Override
    public Collection<Edge> findEdges(final List<String> labels) {
        return findElements((Iterable<Edge>) graph.edges(), labels);
    }

    @Override
    public Collection<Node> getChildren(final Node parent) {
        final Collection<Node> result = new LinkedList<>();
        final Collection<Edge> edges = parent.getOutEdges();
        final Collection<Edge> childRelationEdges = findRelationEdges(edges, ChildRelationEdge.RELATION_NAME);
        for (final Edge childRelation : childRelationEdges) {
            result.add(childRelation.getTargetNode());
        }
        return result;
    }

    protected static <T extends Edge> Collection<T> findRelationEdges(final Iterable<T> elements, final String relationName) {
        final Collection<T> result = new LinkedList<>();
        Iterator<T> elementsIt = elements.iterator();
        while (elementsIt.hasNext()) {
            final T element = elementsIt.next();
            if (element instanceof DefaultEdge) {
                final DefaultEdge defaultEdge = (DefaultEdge) element;
                if (defaultEdge.getRelationName().equals(relationName)) {
                    result.add(element);
                }
            }
        }
        return result;
    }
    
    protected static <T extends Element> Collection<T> findElements(final Iterable<T> elements, final List<String> labels) {
        final Collection<T> result = new LinkedList<>();
        Iterator<T> elementsIt = elements.iterator();
        while (elementsIt.hasNext()) {
            final T element = elementsIt.next();
            if ( contains(element.getLabels(), labels) ) {
                result.add(element);
            }

        }
        return result;
    }

    protected static boolean contains(Collection<String> c1, Collection<String> c2) {
        if ( c1 == null || c1.isEmpty()) return false;
        if ( c2 == null || c2.isEmpty()) return false;
        
        for (String item1 : c1) {
            for (String item2 : c2) {
                if (item1.equals(item2)) return true;
            }
        }
        
        return false;
    }

}
