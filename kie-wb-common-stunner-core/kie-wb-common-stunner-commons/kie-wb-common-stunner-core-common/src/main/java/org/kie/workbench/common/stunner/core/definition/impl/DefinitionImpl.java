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

package org.kie.workbench.common.stunner.core.definition.impl;

import org.jboss.errai.common.client.api.annotations.MapsTo;
import org.jboss.errai.common.client.api.annotations.Portable;
import org.uberfire.commons.validation.PortablePreconditions;

import java.util.Set;

@Portable
public class DefinitionImpl {

    private final String id;
    private final PropertyImpl nameProperty;
    private final String category;
    private final String title;
    private final String description;
    private final Set<String> labels;    
    private final Set<Object> propertySets;
    private final Set<Object> properties;

    public DefinitionImpl(@MapsTo("id") String id,
                          @MapsTo("category") String category,
                          @MapsTo("title") String title,
                          @MapsTo("description") String description,
                          @MapsTo("labels") Set<String> labels,
                          @MapsTo("propertySets") Set<Object> propertySets,
                          @MapsTo("nameProperty")  PropertyImpl nameProperty,
                          @MapsTo("properties") Set<Object> properties) {
        this.category = PortablePreconditions.checkNotNull("category",
                category);
        this.nameProperty = PortablePreconditions.checkNotNull( "nameProperty",
                nameProperty );
        this.title = PortablePreconditions.checkNotNull("title",
                title);
        this.description = PortablePreconditions.checkNotNull("description",
                description);
        this.labels = PortablePreconditions.checkNotNull("labels",
                labels);
        this.id = PortablePreconditions.checkNotNull( "id",
                id );
        this.propertySets = PortablePreconditions.checkNotNull( "propertySets",
                propertySets );
        this.properties = PortablePreconditions.checkNotNull( "properties",
                properties );
    }

    public PropertyImpl getNameProperty() {
        return nameProperty;
    }

    public String getId() {
        return id;
    }

    public String getCategory() {
        return category;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Set<String> getLabels() {
        return labels;
    }

    public Set<Object> getPropertySets() {
        return propertySets;
    }

    public Set<Object> getProperties() {
        return properties;
    }

}
