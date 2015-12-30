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

package org.wirez.core.client.factory;

import org.wirez.core.api.definition.Definition;
import org.wirez.core.client.Shape;
import org.wirez.core.client.factory.control.DefaultShapeControlFactories;
import org.wirez.core.client.factory.control.HasShapeControlFactories;
import org.wirez.core.client.factory.control.ShapeControlFactory;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;

public abstract class BaseShapeFactory<W extends Definition, S extends Shape<W>> 
        implements ShapeFactory<W, S>, HasShapeControlFactories {

    protected DefaultShapeControlFactories defaultShapeControlFactories;
    protected Collection<ShapeControlFactory<?, ?>> DEFAULT_FACTORIES;

    @Inject
    public BaseShapeFactory(DefaultShapeControlFactories defaultShapeControlFactories) {
        this.defaultShapeControlFactories = defaultShapeControlFactories;
    }

    @PostConstruct
    public void init() {
        DEFAULT_FACTORIES = new ArrayList<ShapeControlFactory<?, ?>>() {{
            add( getDragControlFactory() );
            add( getResizeControlFactory() );
        }};
    }

    protected ShapeControlFactory<?, ?> getDragControlFactory() {
        return defaultShapeControlFactories.dragControlFactory();
    }

    protected ShapeControlFactory<?, ?> getResizeControlFactory() {
        return defaultShapeControlFactories.resizeControlFactory();
    }

    @Override
    public Collection<ShapeControlFactory<?, ?>> getFactories() {
        return DEFAULT_FACTORIES;
    }
    
}
