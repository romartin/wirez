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

@Portable
public class DefaultProperty<V> extends BaseHasDefaultValueProperty<V> {
    
    private final PropertyType type;

    public DefaultProperty(@MapsTo("id") final String id, @MapsTo("type") PropertyType type) {
        super(id);
        this.type = type;
    }

    public DefaultProperty(@MapsTo("id") String id,
                           @MapsTo("caption") String caption,
                           @MapsTo("description") String description,
                           @MapsTo("isReadOnly") boolean isReadOnly,
                           @MapsTo("isOptional") boolean isOptional,
                           @MapsTo("isPublic") boolean isPublic,
                           @MapsTo("type") PropertyType type,
                           @MapsTo("defaultValue") V defaultValue) {
        super(id, caption, description, isReadOnly, isOptional, isPublic, defaultValue);
        this.type = type;
    }

    @Override
    public PropertyType getType() {
        return type;
    }

}
