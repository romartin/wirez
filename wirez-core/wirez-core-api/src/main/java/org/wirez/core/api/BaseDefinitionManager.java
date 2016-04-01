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

import org.wirez.core.api.definition.DefinitionSet;
import org.wirez.core.api.definition.adapter.*;
import org.wirez.core.api.diagram.Diagram;
import org.wirez.core.api.registry.DiagramRegistry;

import java.util.*;

public abstract class BaseDefinitionManager implements DefinitionManager {

    DiagramRegistry<? extends Diagram> diagramRegistry;

    protected final List<DefinitionSet> definitionSets = new ArrayList<DefinitionSet>();
    protected final List<DefinitionSetAdapter> definitionSetAdapters = new ArrayList<DefinitionSetAdapter>();
    protected final List<DefinitionSetRuleAdapter> definitionSetRuleAdapters = new ArrayList<DefinitionSetRuleAdapter>();
    protected final List<DefinitionAdapter> definitionAdapters = new ArrayList<DefinitionAdapter>();
    protected final List<PropertySetAdapter> propertySetAdapters = new ArrayList<PropertySetAdapter>();
    protected final List<PropertyAdapter> propertyAdapters = new ArrayList<PropertyAdapter>();

    protected BaseDefinitionManager() {
    }

    public BaseDefinitionManager(final DiagramRegistry<? extends Diagram> diagramRegistry) {
        this.diagramRegistry = diagramRegistry;
    }

    @Override
    public Collection<DefinitionSet> getDefinitionSets() {
        return Collections.unmodifiableCollection( definitionSets );
    }

    @Override
    public DefinitionSet getDefinitionSet(final String id) {
        if ( null != id && id.trim().length() > 0 ) {
            for ( final DefinitionSet definitionSet : definitionSets ) {
                final DefinitionSetAdapter adapter = getDefinitionSetAdapter( definitionSet.getClass() );
                final String defSetId = adapter.getId( definitionSet );
                if ( defSetId.equals(id) ) {
                    return definitionSet;
                }
            }
        }
        
        return null;
    }

    @Override
    public <T> DefinitionSetAdapter<T> getDefinitionSetAdapter(Class<?> pojo) {
        for (DefinitionSetAdapter adapter : definitionSetAdapters) {
            if ( adapter.accepts(pojo) ) {
                return adapter;
            }
        }

        return null;
    }

    @Override
    public <T> DefinitionSetRuleAdapter<T> getDefinitionSetRuleAdapter(Class<?> pojo) {
        for (DefinitionSetRuleAdapter adapter : definitionSetRuleAdapters) {
            if ( adapter.accepts(pojo) ) {
                return adapter;
            }
        }

        return null;
    }

    @Override
    public <T> DefinitionAdapter<T> getDefinitionAdapter(Class<?> pojo) {
        for (DefinitionAdapter adapter : definitionAdapters) {
            if ( adapter.accepts(pojo) ) {
                return adapter;
            }
        }

        return null;
    }

    @Override
    public<T> PropertySetAdapter<T> getPropertySetAdapter(Class<?> pojo) {
        for (PropertySetAdapter adapter : propertySetAdapters) {
            if ( adapter.accepts(pojo) ) {
                return adapter;
            }
        }

        return null;
    }

    @Override
    public<T> PropertyAdapter<T> getPropertyAdapter(Class<?> pojo) {
        for (PropertyAdapter adapter : propertyAdapters) {
            if ( adapter.accepts(pojo) ) {
                return adapter;
            }
        }

        return null;
    }

    public static <T extends Adapter> void sortAdapters(List<T> adapters) {
        Collections.sort(adapters, new Comparator<T>() {
            @Override
            public int compare(T o1, T o2) {
                return o1.getPriority() - o2.getPriority();
            }
        });
    }

}

