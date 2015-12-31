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

/**
 * Any element of a graph that has a view representation associated. A view representation is given by a view definition and the view bounds.
 * @param <W> The view definition.
 */
public interface HasView<W extends Definition> {

    W getDefinition();

    Bounds getBounds();
    
}