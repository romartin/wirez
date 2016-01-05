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

package org.wirez.core.api.graph;

import org.wirez.core.api.definition.Definition;

import java.util.Map;
import java.util.Set;

/**
 * An element of a graph. All graph elements (nodes, edges, etc) must have a unique identifier, a collection of properties, a set of labels (roles) and a generic content. 
 * The content gives the semantics and views, if any, for the element.
 */
public interface Element<C> {
    
    String getUUID();

    Map<String, Object> getProperties();
    
    Set<String> getLabels();
    
    C getContent();
    
}
