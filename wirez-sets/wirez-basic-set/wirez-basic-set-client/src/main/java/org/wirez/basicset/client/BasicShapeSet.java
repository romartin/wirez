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

package org.wirez.basicset.client;

import com.google.gwt.safehtml.shared.SafeUri;
import org.wirez.basicset.api.BasicSet;
import org.wirez.basicset.client.factory.ConnectorFactory;
import org.wirez.basicset.client.factory.RectangleFactory;
import org.wirez.basicset.client.resources.BasicSetImageResources;
import org.wirez.core.api.definition.Definition;
import org.wirez.core.api.definition.DefinitionSet;
import org.wirez.core.client.Shape;
import org.wirez.core.client.ShapeSet;
import org.wirez.core.client.factory.ShapeFactory;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

@ApplicationScoped
public class BasicShapeSet implements ShapeSet {

    public static final String ID = "basic";

    RectangleFactory rectangleFactory;
    ConnectorFactory connectorFactory;
    private List<ShapeFactory<? extends Definition, ? extends Shape>> factories;

    @Inject
    public BasicShapeSet(final RectangleFactory rectangleFactory,
                         final ConnectorFactory connectorFactory) {
        this.rectangleFactory = rectangleFactory;
        this.connectorFactory = connectorFactory;
    }

    @PostConstruct
    public void init() {
        factories = new LinkedList<ShapeFactory<? extends Definition, ? extends Shape>>() {{
            add(rectangleFactory);
            add(connectorFactory);
        }};
    }

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public String getName() {
        return "Basic Shapes";
    }

    @Override
    public String getDescription() {
        return "The basic shapes set";
    }

    @Override
    public SafeUri getThumbnailUri() {
        return BasicSetImageResources.INSTANCE.basicSetThumb().getSafeUri();
    }

    @Override
    public DefinitionSet getDefinitionSet() {
        return BasicSet.INSTANCE;
    }

    @Override
    public Collection<ShapeFactory<? extends Definition, ? extends Shape>> getFactories() {
        return factories;
    }
}
