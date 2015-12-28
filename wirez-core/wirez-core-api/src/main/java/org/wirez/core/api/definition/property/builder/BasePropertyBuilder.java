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

import org.wirez.core.api.definition.property.DefaultProperty;
import org.wirez.core.api.definition.property.Property;
import org.wirez.core.api.definition.property.PropertyType;

public abstract class BasePropertyBuilder<V> implements PropertyBuilder<BasePropertyBuilder, V> {

    DefaultProperty<V> property;

    public BasePropertyBuilder(final String id, final PropertyType type) {
        property = new DefaultProperty<V>(id, type);
    }

    @Override
    public BasePropertyBuilder<V> caption(final String caption) {
        property.setCaption(caption);
        return this;
    }

    @Override
    public BasePropertyBuilder<V> description(final String description) {
        property.setDescription(description);
        return this;
    }

    @Override
    public BasePropertyBuilder<V> readOnly(final boolean readOnly) {
        property.setReadOnly(readOnly);
        return this;
    }

    @Override
    public BasePropertyBuilder<V> optional(final boolean optional) {
        property.setOptional(optional);
        return this;
    }

    @Override
    public BasePropertyBuilder<V> publish(final boolean publish) {
        property.setPublic(publish);
        return this;
    }

    @Override
    public BasePropertyBuilder<V> defaultValue(final V value) {
        property.setDefaultValue(value);
        return this;
    }

    @Override
    public Property build() {
        return property;
    }
}
