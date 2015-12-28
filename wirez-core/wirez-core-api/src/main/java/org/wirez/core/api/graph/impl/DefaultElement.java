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
import org.wirez.core.api.definition.Definition;
import org.wirez.core.api.graph.Bounds;
import org.wirez.core.api.graph.Element;

import java.util.Map;
import java.util.Set;

@Portable
public class DefaultElement<W extends Definition> implements Element<W> {

    protected String uuid;
    protected W definition;
    protected Map<String, Object> properties;
    protected Set<String> labels;
    protected Bounds bounds;

    public DefaultElement(@MapsTo("uuid") String uuid,
                          @MapsTo("definition") W definition,
                          @MapsTo("properties") Map<String, Object> properties,
                          @MapsTo("labels") Set<String> labels,
                          @MapsTo("bounds") Bounds bounds) {
        this.uuid = uuid;
        this.definition = definition;
        this.properties = properties;
        this.labels = labels;
        this.bounds = bounds;
    }

    @Override
    public String getUUID() {
        return uuid;
    }

    @Override
    public Map<String, Object> getProperties() {
        return properties;
    }

    @Override
    public Set<String> getLabels() {
        return labels;
    }

    @Override
    public W getDefinition() {
        return definition;
    }

    @Override
    public Bounds getBounds() {
        return bounds;
    }

    public void setBounds(Bounds bounds) {
        this.bounds = bounds;
    }

    @Override
    public String toString() {
        return "ElementImpl{" +
                "uuid=" + uuid +
                ", def=" + definition +
                ", properties=" + properties +
                '}';
    }

    @Override
    public Element<W> copy() {
        return new DefaultElement<W>(uuid, definition, properties, labels, bounds);
    }
}