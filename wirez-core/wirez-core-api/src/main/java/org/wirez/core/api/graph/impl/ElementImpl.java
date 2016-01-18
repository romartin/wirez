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

package org.wirez.core.api.graph.impl;

import org.jboss.errai.common.client.api.annotations.MapsTo;
import org.jboss.errai.common.client.api.annotations.Portable;
import org.uberfire.commons.validation.PortablePreconditions;
import org.wirez.core.api.definition.Definition;
import org.wirez.core.api.definition.property.Property;
import org.wirez.core.api.graph.Bounds;
import org.wirez.core.api.graph.Element;

import java.util.Map;
import java.util.Set;

@Portable
public class ElementImpl<C> implements Element<C> {

    protected String uuid;
    protected Set<Property> properties;
    protected Set<String> labels;
    protected C content;

    public ElementImpl(@MapsTo("uuid") String uuid,
                       @MapsTo("properties") Set<Property> properties,
                       @MapsTo("labels") Set<String> labels,
                       @MapsTo("content") C content) {
        this.uuid = PortablePreconditions.checkNotNull( "uuid",
                uuid );
        this.properties = PortablePreconditions.checkNotNull( "properties",
                properties );
        this.labels = PortablePreconditions.checkNotNull( "labels",
                labels );
        this.content = PortablePreconditions.checkNotNull( "content",
                content );
    }

    @Override
    public String getUUID() {
        return uuid;
    }

    @Override
    public Set<Property> getProperties() {
        return properties;
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
    public String toString() {
        return "ElementImpl{" +
                "uuid=" + uuid +
                ", properties=" + properties +
                ", labels=" + labels + 
                '}';
    }

}