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

package org.wirez.core.api;

import org.wirez.core.definition.adapter.*;

import java.util.Collection;

public interface DefinitionManager {

    /**
     * Returns all the definitions sets present.
     */
    <T> Collection<T> getDefinitionSets();

    /**
     * Returns a definitions set for a given identifier.
     */
    <T> T getDefinitionSet(String id );

    /**
     * Returns the Definition Set adapter instance for the given pojo's class.
     */
    <T> DefinitionSetAdapter<T> getDefinitionSetAdapter(Class<?> pojoClass );

    /**
     * Returns the Definition Set rules adapter instance for the given pojo's class.
     */
    <T> DefinitionSetRuleAdapter<T> getDefinitionSetRuleAdapter(Class<?> pojoClass );

    /**
     * Returns the Definition adapter instance for the given pojo's class.
     */
    <T> DefinitionAdapter<T> getDefinitionAdapter(Class<?> pojoClass );

    /**
     * Returns the Property Set adapter instance for the given pojo property set's class.
     */
    <T> PropertySetAdapter<T> getPropertySetAdapter(Class<?> pojoClass );

    /**
     * Returns the Property adapter instance for the given pojo property's class.
     */    
    <T> PropertyAdapter<T> getPropertyAdapter(Class<?> pojoClass );
    
    
}
