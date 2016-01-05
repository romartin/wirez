/*
 * Copyright 2016 Red Hat, Inc. and/or its affiliates.
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

package org.wirez.core.client.factory.control;

import org.wirez.core.api.graph.Element;
import org.wirez.core.client.Shape;
import org.wirez.core.client.control.ToolboxControl;
import org.wirez.core.client.control.toolbox.Toolbox;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class DefaultToolboxControlFactory<S extends Shape, E extends Element> implements ShapeControlFactory<S, ToolboxControl<S, E>> {
    
    Toolbox toolbox;

    @Inject
    public DefaultToolboxControlFactory(Toolbox toolbox) {
        this.toolbox = toolbox;
    }

    @Override
    public ToolboxControl<S, E> build(S shape) {
        return new ToolboxControl<S, E>(toolbox);
    }
    
}
