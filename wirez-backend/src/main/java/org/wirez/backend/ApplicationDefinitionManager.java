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

package org.wirez.backend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wirez.core.api.AbstractDefinitionManager;
import org.wirez.core.definition.DefinitionSetProxy;
import org.wirez.core.definition.adapter.*;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

@ApplicationScoped
public class ApplicationDefinitionManager extends AbstractDefinitionManager {

    private static final Logger LOG = LoggerFactory.getLogger(ApplicationDefinitionManager.class);

    Instance<DefinitionSetProxy<?>> definitionSetsInstances;
    Instance<DefinitionSetAdapter<?>> definitionSetAdapterInstances;
    Instance<DefinitionSetRuleAdapter<?>> definitionSetRuleAdapterInstances;
    Instance<DefinitionAdapter<?>> definitionAdapterInstances;
    Instance<PropertySetAdapter<?>> propertySetAdapterInstances;
    Instance<PropertyAdapter<?, ?>> propertyAdapterInstances;
    Instance<MorphAdapter<?, ?>> morphAdapterInstances;

    protected ApplicationDefinitionManager() {
    }

    @Inject
    public ApplicationDefinitionManager(Instance<DefinitionSetProxy<?>> definitionSetsInstances,
                                        Instance<DefinitionSetAdapter<?>> definitionSetAdapterInstances,
                                        Instance<DefinitionSetRuleAdapter<?>> definitionSetRuleAdapterInstances,
                                        Instance<DefinitionAdapter<?>> definitionAdapterInstances,
                                        Instance<PropertySetAdapter<?>> propertySetAdapterInstances,
                                        Instance<PropertyAdapter<?, ?>> propertyAdapterInstances,
                                        Instance<MorphAdapter<?, ?>> morphAdapterInstances) {
        this.definitionSetsInstances = definitionSetsInstances;
        this.definitionSetAdapterInstances = definitionSetAdapterInstances;
        this.definitionSetRuleAdapterInstances = definitionSetRuleAdapterInstances;
        this.definitionAdapterInstances = definitionAdapterInstances;
        this.propertySetAdapterInstances = propertySetAdapterInstances;
        this.propertyAdapterInstances = propertyAdapterInstances;
        this.morphAdapterInstances = morphAdapterInstances;
    }

    @PostConstruct
    public void init() {
        initDefSets();
        initAdapters();
        initMorphAdapters();
    }
    
    private void initDefSets() {
        for (DefinitionSetProxy definitionSet : definitionSetsInstances ) {
            definitionSets.add( definitionSet.getDefinitionSet() );
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

    private void initMorphAdapters() {
        for ( MorphAdapter morphAdapter : morphAdapterInstances ) {
            morphAdapters.add( morphAdapter );
        }
    }

}

