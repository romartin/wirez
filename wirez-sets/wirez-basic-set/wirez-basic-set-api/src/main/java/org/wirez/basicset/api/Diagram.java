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

import org.wirez.core.api.definition.DefaultContent;
import org.wirez.core.api.definition.property.Property;
import org.wirez.core.api.definition.property.PropertySet;
import org.wirez.core.api.definition.property.set.ElementPropertySetBuilder;
import org.wirez.core.api.definition.property.set.NamePropertyBuilder;

import java.util.HashSet;
import java.util.Set;

@Portable
public class Diagram extends BasicDefinition {

    public static final String ID = "basicDiagram";

    public Diagram() {
        super(ID);
        setContent(new DefaultContent(BasicSetCategories.INSTANCE.DIAGRAM,
                "Basic Diagram",
                "A basic shapes diagram",
                new HashSet<String>(),
                propertySets,
                properties));
            
    }

    private Set<Property> properties = new HashSet<Property>() {{
        
    }};

    private Set<PropertySet> propertySets= new HashSet<PropertySet>() {{
        add( 
                new ElementPropertySetBuilder()
                    .replaceProperty(
                        new NamePropertyBuilder().defaultValue("My diagram").build()
                ).build() 
            );
    }};
    
}
