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

package org.wirez.core.backend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wirez.core.api.BaseDefinitionManager;
import org.wirez.core.api.adapter.*;
import org.wirez.core.api.definition.Definition;
import org.wirez.core.api.definition.DefinitionSet;
import org.wirez.core.api.definition.property.Property;
import org.wirez.core.api.definition.property.PropertySet;
import org.wirez.core.api.rule.Rule;
import org.wirez.core.backend.adapter.AnnotatedPropertySetAdapter;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

@ApplicationScoped
public class BackendDefinitionManager extends BaseDefinitionManager {

    private static final Logger LOG = LoggerFactory.getLogger(BackendDefinitionManager.class);

    Instance<DefinitionSet> definitionSetInstances;
    Instance<DefinitionSetAdapter<? extends DefinitionSet>> definitionSetAdapterInstances;
    Instance<DefinitionSetRuleAdapter<? extends DefinitionSet>> definitionSetRuleAdapterInstances;
    Instance<DefinitionAdapter<? extends Definition>> definitionAdapterInstances;
    Instance<PropertySetAdapter<? extends PropertySet>> propertySetAdapterInstances;
    Instance<PropertyAdapter<? extends Property>> propertyAdapterInstances;
    
    @Inject
    public BackendDefinitionManager(Instance<DefinitionSet> definitionSetInstances, 
                                    Instance<DefinitionSetAdapter<? extends DefinitionSet>> definitionSetAdapterInstances,
                                    Instance<DefinitionSetRuleAdapter<? extends DefinitionSet>> definitionSetRuleAdapterInstances,
                                    Instance<DefinitionAdapter<? extends Definition>> definitionAdapterInstances, 
                                    Instance<PropertySetAdapter<? extends PropertySet>> propertySetAdapterInstances, 
                                    Instance<PropertyAdapter<? extends Property>> propertyAdapterInstances) {
        this.definitionSetInstances = definitionSetInstances;
        this.definitionSetAdapterInstances = definitionSetAdapterInstances;
        this.definitionSetRuleAdapterInstances = definitionSetRuleAdapterInstances;
        this.definitionAdapterInstances = definitionAdapterInstances;
        this.propertySetAdapterInstances = propertySetAdapterInstances;
        this.propertyAdapterInstances = propertyAdapterInstances;
    }

    @PostConstruct
    public void init() {
        initDefinitionSets();
        initAdapters();
    }

    private void initDefinitionSets() {
        for (DefinitionSet definitionSet : definitionSetInstances) {
            definitionSets.add(definitionSet);
        }
    }

    private void initAdapters() {
        for (DefinitionSetAdapter definitionSetAdapter : definitionSetAdapterInstances) {
            definitionSetAdapters.add(definitionSetAdapter);
        }
        sortAdapters(definitionSetAdapters);
        for (DefinitionSetRuleAdapter definitionSetRuleAdapter : definitionSetRuleAdapterInstances) {
            definitionSetRuleAdapters.add(definitionSetRuleAdapter);
        }
        sortAdapters(definitionSetRuleAdapters);
        for (DefinitionAdapter definitionAdapter : definitionAdapterInstances) {
            definitionAdapters.add(definitionAdapter);
        }
        sortAdapters(definitionAdapters);
        for (PropertySetAdapter propertySetAdapter : propertySetAdapterInstances) {
            propertySetAdapters.add(propertySetAdapter);
        }
        sortAdapters(propertySetAdapters);
        for (PropertyAdapter propertyAdapter : propertyAdapterInstances) {
            propertyAdapters.add(propertyAdapter);
        }
        sortAdapters(propertyAdapters);
    }

}

