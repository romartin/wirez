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

package org.wirez.core.api.graph.processing;

import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Element;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.impl.ChildRelationship;
import org.wirez.core.api.graph.impl.DefaultGraph;

import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Default implementation for graph handling and querying.
 * TODO: Review and apply better performance pattern for querying the graph.
 */
@ApplicationScoped
public class DefaultGraphHandler implements GraphHandler {

    /**
     * Returns the element (node or edge) with the given uuid.
     */
    public Element get(final DefaultGraph graph, final String uuid) {
        assert graph != null && uuid != null;
        Element element = graph.getNode(uuid);
        if (element == null) {
            element = graph.getEdge(uuid);
        }
        return element;
    }

    public Node getNode(final DefaultGraph graph, final String uuid) {
        if ( null != uuid ) {
            return graph.getNode(uuid);
        }
        return null;
    }

    public Edge getEdge(final DefaultGraph graph, final String uuid) {
        if ( null != uuid ) {
            return graph.getEdge(uuid);
        }
        return null;
    }

    @Override
    public Collection<Node> getChildren(Node parent) {
        final Collection<Node> result = new ArrayList<>();
        if ( null != parent ) {
            List<Edge> edges = parent.getOutEdges();
            if ( null != edges ) {
                for (final Edge edge : edges) {
                    if (edge instanceof ChildRelationship) {
                        ChildRelationship childRelationship = (ChildRelationship) edge;
                        final Node childNode = childRelationship.getTargetNode();
                        if ( null != childNode ) {
                            result.add(childNode);
                        }
                    }
                }
            }
        }
        
        return result;
    }

}
