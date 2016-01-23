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

package org.wirez.core.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wirez.core.api.adapter.*;
import org.wirez.core.api.definition.DefinitionSet;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import java.util.*;

public abstract class BaseDefinitionManager implements DefinitionManager {

    protected final List<DefinitionSet> definitionSets = new ArrayList<DefinitionSet>();
    protected final List<DefinitionSetAdapter> definitionSetAdapters = new ArrayList<DefinitionSetAdapter>();
    protected final List<DefinitionSetRuleAdapter> definitionSetRuleAdapters = new ArrayList<DefinitionSetRuleAdapter>();
    protected final List<DefinitionAdapter> definitionAdapters = new ArrayList<DefinitionAdapter>();
    protected final List<PropertySetAdapter> propertySetAdapters = new ArrayList<PropertySetAdapter>();
    protected final List<PropertyAdapter> propertyAdapters = new ArrayList<PropertyAdapter>();

    @Override
    public DefinitionSet getDefinitionSet(final String id) {
        if (null != id) {
            for (DefinitionSet definitionSet : definitionSets) {
                if (definitionSet.getId().equals(id)) {
                    return definitionSet;
                }
            }
        }
        return null;
    }

    @Override
    public DefinitionSetAdapter getDefinitionSetAdapter(Class definitionSetClass) {
        for (DefinitionSetAdapter adapter : definitionSetAdapters) {
            if ( adapter.accepts(definitionSetClass) ) {
                return adapter;
            }
        }

        return null;
    }

    @Override
    public DefinitionSetRuleAdapter getDefinitionSetRuleAdapter(Class definitionSetClass) {
        for (DefinitionSetRuleAdapter adapter : definitionSetRuleAdapters) {
            if ( adapter.accepts(definitionSetClass) ) {
                return adapter;
            }
        }

        return null;
    }

    @Override
    public DefinitionAdapter getDefinitionAdapter(Class definitionClass) {
        for (DefinitionAdapter adapter : definitionAdapters) {
            if ( adapter.accepts(definitionClass) ) {
                return adapter;
            }
        }

        return null;
    }

    @Override
    public PropertySetAdapter getPropertySetAdapter(Class propertySetClass) {
        for (PropertySetAdapter adapter : propertySetAdapters) {
            if ( adapter.accepts(propertySetClass) ) {
                return adapter;
            }
        }

        return null;
    }

    @Override
    public PropertyAdapter getPropertyAdapter(Class propertyClass) {
        for (PropertyAdapter adapter : propertyAdapters) {
            if ( adapter.accepts(propertyClass) ) {
                return adapter;
            }
        }

        return null;
    }

    @Override
    public Collection<DefinitionSet> getDefinitionSets() {
        return definitionSets;
    }

    protected static <T extends Adapter> void sortAdapters(List<T> adapters) {
        Collections.sort(adapters, new Comparator<T>() {
            @Override
            public int compare(T o1, T o2) {
                return o1.getPriority() - o2.getPriority();
            }
        });
    }

}

