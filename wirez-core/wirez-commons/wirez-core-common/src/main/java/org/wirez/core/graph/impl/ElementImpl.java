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

package org.wirez.core.graph.impl;

import org.jboss.errai.common.client.api.annotations.MapsTo;
import org.jboss.errai.common.client.api.annotations.Portable;
import org.uberfire.commons.validation.PortablePreconditions;
import org.wirez.core.graph.Edge;
import org.wirez.core.graph.Element;
import org.wirez.core.graph.Node;

import java.util.LinkedHashSet;
import java.util.Set;

@Portable
public class ElementImpl<C> implements Element<C> {

    private final String uuid;
    private final Set<String> labels = new LinkedHashSet<>();
    private C content;

    public ElementImpl( @MapsTo( "uuid" ) String uuid ) {
        this.uuid = PortablePreconditions.checkNotNull( "uuid",
                uuid );
    }

    @Override
    public String getUUID() {
        return uuid;
    }

    @Override
    public Set<String> getLabels() {
        return labels;
    }

    @Override
    public C getContent() {
        return content;
    }

    @Override
    public void setContent( final C content ) {
        this.content = content;
    }

    @Override
    @SuppressWarnings( "unchecked" )
    public Node<C, Edge> asNode() {
        return ( Node<C, Edge> ) this;
    }

    @Override
    @SuppressWarnings( "unchecked" )
    public Edge<C, Node> asEdge() {
        return ( Edge<C, Node> ) this;
    }

    @Override
    public boolean equals( final Object o ) {
        if ( this == o ) {
            return true;
        }
        if ( !( o instanceof ElementImpl ) ) {
            return false;
        }

        ElementImpl that = ( ElementImpl ) o;

        return uuid.equals( that.uuid );
    }

    @Override
    public String toString() {
        return "ElementImpl{" +
                "uuid=" + uuid +
                ", labels=" + labels +
                '}';
    }

}