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
import org.wirez.core.api.adapter.DefinitionAdapter;
import org.wirez.core.api.adapter.DefinitionSetAdapter;
import org.wirez.core.api.adapter.PropertyAdapter;
import org.wirez.core.api.adapter.PropertySetAdapter;
import org.wirez.core.api.adapter.shared.DefaultDefinitionAdapter;
import org.wirez.core.api.adapter.shared.DefaultDefinitionSetAdapter;
import org.wirez.core.api.adapter.shared.DefaultPropertyAdapter;
import org.wirez.core.api.adapter.shared.DefaultPropertySetAdapter;
import org.wirez.core.api.definition.DefinitionSet;
import org.wirez.core.api.registry.RuleRegistry;
import org.wirez.core.api.rule.Rule;
import org.wirez.core.backend.adapter.AnnotatedDefinitionAdapter;
import org.wirez.core.backend.adapter.AnnotatedDefinitionSetAdapter;
import org.wirez.core.backend.adapter.AnnotatedPropertyAdapter;
import org.wirez.core.backend.adapter.AnnotatedPropertySetAdapter;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

@ApplicationScoped
public class BackendDefinitionManager extends BaseDefinitionManager {

    private static final Logger LOG = LoggerFactory.getLogger(BackendDefinitionManager.class);

    Instance<DefinitionSet> definitionSetInstances;
    Instance<DefinitionSetAdapter> definitionSetAdapterInstances;
    Instance<DefinitionAdapter> definitionAdapterInstances;
    Instance<PropertySetAdapter> propertySetAdapterInstances;
    Instance<PropertyAdapter> propertyAdapterInstances;
    RuleRegistry<Rule> ruleRuleRegistry;
    
    @Inject
    public BackendDefinitionManager(Instance<DefinitionSet> definitionSetInstances, 
                                    Instance<DefinitionSetAdapter> definitionSetAdapterInstances, 
                                    Instance<DefinitionAdapter> definitionAdapterInstances, 
                                    Instance<PropertySetAdapter> propertySetAdapterInstances, 
                                    Instance<PropertyAdapter> propertyAdapterInstances,
                                    RuleRegistry<Rule> ruleRuleRegistry) {
        this.definitionSetInstances = definitionSetInstances;
        this.definitionSetAdapterInstances = definitionSetAdapterInstances;
        this.definitionAdapterInstances = definitionAdapterInstances;
        this.propertySetAdapterInstances = propertySetAdapterInstances;
        this.propertyAdapterInstances = propertyAdapterInstances;
        this.ruleRuleRegistry = ruleRuleRegistry;
    }

    @PostConstruct
    public void init() {
        initDefinitionSets();
        // initAdapters();
        initAdaptersTEMP();
    }

    private void initDefinitionSets() {
        for (DefinitionSet definitionSet : definitionSetInstances) {
            definitionSets.add(definitionSet);
        }
    }

    // TODO: Adapter injection not working - should inject two instances for each adapter: the default and the annotated ones.
    private void initAdapters() {
        for (DefinitionSetAdapter definitionSetAdapter : definitionSetAdapterInstances) {
            definitionSetAdapters.add(definitionSetAdapter);
        }
        for (DefinitionAdapter definitionAdapter : definitionAdapterInstances) {
            definitionAdapters.add(definitionAdapter);
        }
        for (PropertySetAdapter propertySetAdapter : propertySetAdapterInstances) {
            propertySetAdapters.add(propertySetAdapter);
        }
        for (PropertyAdapter propertyAdapter : propertyAdapterInstances) {
            propertyAdapters.add(propertyAdapter);
        }
    }

    // TODO: Remove when injections working.
    private void initAdaptersTEMP() {
        definitionSetAdapters.add(new DefaultDefinitionSetAdapter());
        definitionSetAdapters.add(new AnnotatedDefinitionSetAdapter(ruleRuleRegistry));
        definitionAdapters.add(new DefaultDefinitionAdapter());
        definitionAdapters.add(new AnnotatedDefinitionAdapter(new AnnotatedPropertyAdapter(), ruleRuleRegistry));
        propertySetAdapters.add(new DefaultPropertySetAdapter());
        propertySetAdapters.add(new AnnotatedPropertySetAdapter());
        propertyAdapters.add(new DefaultPropertyAdapter());
        propertyAdapters.add(new AnnotatedPropertyAdapter());
    }

}

