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

import org.wirez.core.api.definition.DefinitionSet;
import org.wirez.core.api.definition.adapter.*;

import java.util.Collection;

public interface DefinitionManager {

    Collection<DefinitionSet> getDefinitionSets();

    DefinitionSet getDefinitionSet( String id );

    <T> DefinitionSetAdapter<T> getDefinitionSetAdapter(Class<?> pojo);

    <T> DefinitionSetRuleAdapter<T> getDefinitionSetRuleAdapter(Class<?> pojo);
    
    <T> DefinitionAdapter<T> getDefinitionAdapter(Class<?> pojo);

    <T> PropertySetAdapter<T> getPropertySetAdapter(Class<?> pojo);

    <T> PropertyAdapter<T> getPropertyAdapter(Class<?> pojo);
    
    
}
