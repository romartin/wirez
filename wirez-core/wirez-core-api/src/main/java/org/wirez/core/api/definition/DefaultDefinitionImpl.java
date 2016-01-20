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

package org.wirez.core.api.definition;

import org.jboss.errai.common.client.api.annotations.MapsTo;
import org.jboss.errai.common.client.api.annotations.Portable;
import org.uberfire.commons.validation.PortablePreconditions;
import org.wirez.core.api.definition.property.Property;
import org.wirez.core.api.definition.property.PropertySet;

import java.util.Set;

@Portable
public class DefaultDefinitionImpl implements DefaultDefinition {

    private final String id;
    private final String category;
    private final String title;
    private final String description;
    private final Set<String> labels;
    private final Set<PropertySet> propertySets;
    private final Set<Property> properties;

    public DefaultDefinitionImpl(@MapsTo("id") String id,
                                 @MapsTo("category") String category,
                                 @MapsTo("title") String title,
                                 @MapsTo("description") String description,
                                 @MapsTo("labels") Set<String> labels,
                                 @MapsTo("propertySets") Set<PropertySet> propertySets,
                                 @MapsTo("properties") Set<Property> properties) {
        this.id = PortablePreconditions.checkNotNull( "id",
                id );
        this.category = PortablePreconditions.checkNotNull( "category",
                category );
        this.title = PortablePreconditions.checkNotNull( "title",
                title );
        this.description = PortablePreconditions.checkNotNull( "description",
                description );
        this.labels = PortablePreconditions.checkNotNull( "labels",
                labels );
        this.propertySets = PortablePreconditions.checkNotNull( "propertySets",
                propertySets );
        this.properties = PortablePreconditions.checkNotNull( "properties",
                properties );
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getCategory() {
        return category;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public Set<String> getLabels() {
        return labels;
    }

    @Override
    public Set<PropertySet> getPropertySets() {
        return propertySets;
    }

    @Override
    public Set<Property> getProperties() {
        return properties;
    }

}
