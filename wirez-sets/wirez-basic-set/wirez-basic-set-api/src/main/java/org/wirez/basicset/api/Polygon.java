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

package org.wirez.basicset.api;

import org.jboss.errai.common.client.api.annotations.Portable;
import org.wirez.basicset.api.property.RadiusBuilder;
import org.wirez.basicset.api.property.bgset.BackgroundPropertySetBuilder;
import org.wirez.basicset.api.property.bgset.BgColorBuilder;
import org.wirez.basicset.api.property.font.FontPropertySetBuilder;
import org.wirez.core.api.definition.DefaultContent;
import org.wirez.core.api.definition.property.Property;
import org.wirez.core.api.definition.property.PropertySet;
import org.wirez.core.api.definition.property.defaultset.DefaultPropertySetBuilder;
import org.wirez.core.api.definition.property.defaultset.NameBuilder;

import java.util.HashSet;
import java.util.Set;

@Portable
public class Polygon extends BasicNodeDefinition<Polygon> {

    public static final String ID = "polygon";
    public static final String COLOR = "#ff1a1a";
    public static final int RADIUS = 25;


    private final Set<Property> properties = new HashSet<Property>() {{
        add( new RadiusBuilder().defaultValue(RADIUS).build() );
    }};

    private final Set<String> labels = new HashSet<String>() {{
        add( "all" );
        add( "polygon" );
    }};

    private final Set<PropertySet> propertySets = new HashSet<PropertySet>() {{
        add(new DefaultPropertySetBuilder()
                .withProperty(new NameBuilder().defaultValue("My polygon").build())
                .build()
        );
        add (new BackgroundPropertySetBuilder()
                .withProperty(new BgColorBuilder().defaultValue(COLOR).build())
                .build() 
        );
        add( new FontPropertySetBuilder().build() );
    }};
    
    public Polygon() {
        super(ID);
        setContent(new DefaultContent(BasicSetCategories.INSTANCE.BASIC,
                "Polygon",
                "A polygon.",
                labels,
                propertySets,
                properties));
    }

    @Override
    public double getWidth() {
        return RADIUS * 2;
    }

    @Override
    public double getHeight() {
        return RADIUS * 2;
    }
}
