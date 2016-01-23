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
import org.wirez.core.api.rule.Rule;

import java.util.Set;

@Portable
public class DefaultDefinitionImpl extends BaseDefinition implements DefaultDefinition {

    private final String id;
    private final Set<PropertySet> propertySets;
    private final Set<Property> properties;

    public DefaultDefinitionImpl(@MapsTo("id") String id,
                                 @MapsTo("category") String category,
                                 @MapsTo("title") String title,
                                 @MapsTo("description") String description,
                                 @MapsTo("labels") Set<String> labels,
                                 @MapsTo("propertySets") Set<PropertySet> propertySets,
                                 @MapsTo("properties") Set<Property> properties) {
        super(category, title, description, labels);
        this.id = PortablePreconditions.checkNotNull( "id",
                id );
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
    public Set<PropertySet> getPropertySets() {
        return propertySets;
    }

    @Override
    public Set<Property> getProperties() {
        return properties;
    }

}
