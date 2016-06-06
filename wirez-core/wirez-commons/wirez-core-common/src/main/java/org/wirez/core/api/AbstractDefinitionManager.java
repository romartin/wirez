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

import org.wirez.core.definition.adapter.binding.BindableAdapterUtils;
import org.wirez.core.definition.adapter.*;
import org.wirez.core.definition.adapter.MorphAdapter;

import java.util.*;
import java.util.logging.Logger;

public abstract class AbstractDefinitionManager implements DefinitionManager {

    private static Logger LOGGER = Logger.getLogger(AbstractDefinitionManager.class.getName());
    
    protected final List definitionSets = new LinkedList<>();
    protected final List<DefinitionSetAdapter> definitionSetAdapters = new LinkedList<>();
    protected final List<DefinitionSetRuleAdapter> definitionSetRuleAdapters = new LinkedList<>();
    protected final List<DefinitionAdapter> definitionAdapters = new LinkedList<>();
    protected final List<PropertySetAdapter> propertySetAdapters = new ArrayList<PropertySetAdapter>();
    protected final List<PropertyAdapter> propertyAdapters = new LinkedList<>();
    protected final List<MorphAdapter> morphAdapters = new LinkedList<>();

    public AbstractDefinitionManager() {
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> Collection<T> getDefinitionSets() {
        return Collections.unmodifiableCollection((Collection<? extends T>) definitionSets);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getDefinitionSet(final String id) {
        if ( null != id && id.trim().length() > 0 ) {
            for ( final Object definitionSet : definitionSets ) {
                final DefinitionSetAdapter adapter = getDefinitionSetAdapter( definitionSet.getClass() );
                final String defSetId = adapter.getId( definitionSet );
                if ( defSetId.equals( id ) ) {
                    return (T) definitionSet;
                }
            }
        }
        
        return null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> DefinitionSetAdapter<T> getDefinitionSetAdapter(final Class<?> type) {
        final Class<?> clazz = handleBindableProxyClass( type );
        for (DefinitionSetAdapter adapter : definitionSetAdapters) {
            if ( adapter.accepts( clazz ) ) {
                return adapter;
            }
        }

        return nullHandling("Definition Set", type);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> DefinitionSetRuleAdapter<T> getDefinitionSetRuleAdapter(final Class<?> type) {
        final Class<?> clazz = handleBindableProxyClass( type );
        for (DefinitionSetRuleAdapter adapter : definitionSetRuleAdapters) {
            if ( adapter.accepts( clazz ) ) {
                return adapter;
            }
        }

        return nullHandling("Definition Set rules", type);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> DefinitionAdapter<T> getDefinitionAdapter(final Class<?> type) {
        final Class<?> clazz = handleBindableProxyClass( type );
        for (DefinitionAdapter adapter : definitionAdapters) {
            if ( adapter.accepts( clazz ) ) {
                return adapter;
            }
        }

        return nullHandling("Definition", type);
    }

    @Override
    @SuppressWarnings("unchecked")
    public<T> PropertySetAdapter<T> getPropertySetAdapter(final Class<?> type) {
        final Class<?> clazz = handleBindableProxyClass( type );
        for (PropertySetAdapter adapter : propertySetAdapters) {
            if ( adapter.accepts( clazz ) ) {
                return adapter;
            }
        }

        return nullHandling("Property Set", type);
    }

    @Override
    @SuppressWarnings("unchecked")
    public<T> PropertyAdapter<T, ?> getPropertyAdapter(final Class<?> type) {
        final Class<?> clazz = handleBindableProxyClass( type );
        for (PropertyAdapter adapter : propertyAdapters) {
            if ( adapter.accepts( clazz ) ) {
                return adapter;
            }
        }

        return nullHandling("Property", type);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> Iterable<MorphAdapter<T, ?>> getMorphAdapters(final Class<?> type) {
        
        final List<MorphAdapter<T, ?>> result = new LinkedList<>();
        
        final Class<?> clazz = handleBindableProxyClass( type );
        
        for ( MorphAdapter adapter : morphAdapters ) {
            
            if ( adapter.accepts( clazz ) ) {
                result.add( adapter );
            }
        }
        
        return result.isEmpty() ? null : result;
    }

    private Class<?> handleBindableProxyClass(final Class<?> type) {
        return BindableAdapterUtils.handleBindableProxyClass( type );
    }

    public static <T extends PriorityAdapter> void sortAdapters(List<T> adapters) {
        Collections.sort(adapters, (o1, o2) -> o1.getPriority() - o2.getPriority());
    }
    
    private <T> T nullHandling(String domain, Class<?> type) {
        final String message = "No " + domain + " adapter found for pojo with class [" + type.getName() + "]";
        LOGGER.severe( message );
        throw new NullPointerException( message );
    }

}

