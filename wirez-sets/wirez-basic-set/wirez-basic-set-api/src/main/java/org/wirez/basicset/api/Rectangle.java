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

package org.wirez.basicset.api;

import org.jboss.errai.common.client.api.annotations.Portable;
import org.wirez.basicset.api.property.bgset.BackgroundPropertySetBuilder;
import org.wirez.basicset.api.property.bgset.BgColorBuilder;
import org.wirez.core.api.definition.DefaultContent;
import org.wirez.core.api.definition.property.Property;
import org.wirez.core.api.definition.property.PropertySet;
import org.wirez.core.api.definition.property.defaultset.DefaultPropertySetBuilder;
import org.wirez.core.api.definition.property.defaultset.NameBuilder;
import org.wirez.core.api.graph.Bounds;
import org.wirez.core.api.graph.factory.DefaultNodeFactory;
import org.wirez.core.api.graph.impl.DefaultEdge;
import org.wirez.core.api.graph.impl.DefaultNode;
import org.wirez.core.api.graph.impl.DefaultNodeImpl;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Portable
public class Rectangle extends BasicDefinition implements DefaultNodeFactory<Rectangle> {

    public static final String ID = "rectangle";
    public static final String COLOR = "#00CC00";

    private final Set<Property> properties = new HashSet<Property>() {{
        
    }};

    private final Set<PropertySet> propertySets = new HashSet<PropertySet>() {{
        add(new DefaultPropertySetBuilder()
                .withProperty(new NameBuilder().defaultValue("My rectangle").build())
                .build()
        );
        add (new BackgroundPropertySetBuilder()
                .withProperty(new BgColorBuilder().defaultValue(COLOR).build())
                .build() 
        );
    }};
    
    public Rectangle() {
        super(ID);
        setContent(new DefaultContent(BasicSetCategories.INSTANCE.BASIC,
                "Rectangle",
                "A rectangle.",
                new HashSet<String>(),
                propertySets,
                properties));
    }


    @Override
    public DefaultNode<Rectangle, DefaultEdge> build(final String uuid,
                                                     final Set<String> labels,
                                                     final Map<String, Object> properties,
                                                     final Bounds bounds) {
        return new DefaultNodeImpl<Rectangle>(uuid, this, properties, labels, bounds);
    }
}
