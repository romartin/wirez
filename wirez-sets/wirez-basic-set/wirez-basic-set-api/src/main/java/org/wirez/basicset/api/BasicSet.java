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
import org.wirez.basicset.api.property.font.FontPropertySetBuilder;
import org.wirez.core.api.definition.Definition;
import org.wirez.core.api.definition.DefinitionSet;
import org.wirez.core.api.definition.property.PropertySet;
import org.wirez.core.api.definition.property.defaultset.DefaultPropertySetBuilder;
import org.wirez.core.api.rule.Rule;

import javax.enterprise.context.ApplicationScoped;
import java.util.Collection;
import java.util.HashSet;

@ApplicationScoped
@Portable
public class BasicSet implements DefinitionSet {

    public static final BasicSet INSTANCE = new BasicSet();
    public static final String ID = "basicSet";
    
    private final Collection<PropertySet> propertySets = new HashSet<PropertySet>() {{
        add( new DefaultPropertySetBuilder().build() );
        add (new BackgroundPropertySetBuilder().build() );
        add (new FontPropertySetBuilder().build() );
    }};


    private final Collection<Definition> definitions = new HashSet<Definition>() {{
        add( new Diagram() );
        add( new Rectangle() );
        add( new Circle() );
        add( new Connector() );
    }};
    
    @Override
    public String getDomain() {
        return "org.wirez.basicset";
    }

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public String getDescription() {
        return "Basic Shapes Set";
    }

    @Override
    public Collection<PropertySet> getPropertySets() {
        return propertySets;
    }

    @Override
    public Collection<Definition> getDefinitions() {
        return definitions;
    }

    @Override
    public Collection<Rule> getRules() {
        return null;
    }
    
}
