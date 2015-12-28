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

package org.wirez.core.api.definition.property.builder;

import org.wirez.core.api.definition.property.DefaultPropertySet;
import org.wirez.core.api.definition.property.Property;

import java.util.ArrayList;
import java.util.List;

public abstract class BasePropertySetBuilder<P extends DefaultPropertySet> implements PropertySetBuilder<BasePropertySetBuilder, P> {

    protected P propertySet;
    protected List<Property> properties;

    public BasePropertySetBuilder() {
        init();
    }

    protected void init() {
        properties = new ArrayList<Property>();
    }
    
    @Override
    public BasePropertySetBuilder id(final String id) {
        propertySet.setId(id);
        return this;
    }

    @Override
    public BasePropertySetBuilder name(String name) {
        propertySet.setName(name);
        return this;
    }

    @Override
    public BasePropertySetBuilder replaceProperty(final Property property) {
        final int index = properties.indexOf(property);
        if (index > -1) {
            final Property p = properties.get(index);
            propertySet.getProperties().remove(p);
        }
        propertySet.getProperties().add(property);
        return this;
    }

    @Override
    public P build() {
        return propertySet;
    }
    
}
