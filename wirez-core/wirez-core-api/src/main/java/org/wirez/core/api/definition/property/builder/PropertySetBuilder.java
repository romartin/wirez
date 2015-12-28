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

import org.wirez.core.api.definition.property.Property;
import org.wirez.core.api.definition.property.PropertySet;

public interface PropertySetBuilder<T, P extends PropertySet> {

    /**
     * Sets the property set identifier.
     * @param id The identifier.
     * @return The property set builder instance.
     */
    T id(String id);

    /**
     * Sets the property set name.
     * @param name The name.
     * @return The property set builder instance.
     */
    T name(String name);

    /**
     * Adds a property in the property set definition.
     * If the property exist, it must be replaced.
     * 
     * @param property The property to add.
     * @return The property set builder instance.
     */
    T withProperty(Property property);

    /**
     * Builds the property set builder instance.
     * @return The property set builder instance.
     */
    P build();
    
}
