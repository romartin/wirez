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

package org.wirez.core.api.definition.property;

import org.jboss.errai.common.client.api.annotations.MapsTo;
import org.jboss.errai.common.client.api.annotations.Portable;

import java.util.Collection;

@Portable
public class DefaultPropertySet implements PropertySet {

    protected String id;
    protected String name;
    protected Collection<Property> properties;

    public DefaultPropertySet(@MapsTo("id") String id, 
                              @MapsTo("name") String name, 
                              @MapsTo("properties") Collection<Property> properties) {
        this.id = id;
        this.name = name;
        this.properties = properties;
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    @Override
    public Collection<Property> getProperties() {
        return properties;
    }

}
