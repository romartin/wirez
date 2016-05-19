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

    public AbstractDefinitionManager() {
    }

    @Override
    public <T> Collection<T> getDefinitionSets() {
        return Collections.unmodifiableCollection((Collection<? extends T>) definitionSets);
    }

    @Override
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
    public <T> DefinitionSetAdapter<T> getDefinitionSetAdapter(final Class<?> pojoClass) {
        final Class<?> clazz = handleBindableProxyClass( pojoClass );
        for (DefinitionSetAdapter adapter : definitionSetAdapters) {
            if ( adapter.accepts( clazz ) ) {
                return adapter;
            }
        }

        return nullHandling("Definition Set", pojoClass);
    }

    @Override
    public <T> DefinitionSetRuleAdapter<T> getDefinitionSetRuleAdapter(final Class<?> pojoClass) {
        final Class<?> clazz = handleBindableProxyClass( pojoClass );
        for (DefinitionSetRuleAdapter adapter : definitionSetRuleAdapters) {
            if ( adapter.accepts( clazz ) ) {
                return adapter;
            }
        }

        return nullHandling("Definition Set rules", pojoClass);
    }

    @Override
    public <T> DefinitionAdapter<T> getDefinitionAdapter(final Class<?> pojoClass) {
        final Class<?> clazz = handleBindableProxyClass( pojoClass );
        for (DefinitionAdapter adapter : definitionAdapters) {
            if ( adapter.accepts( clazz ) ) {
                return adapter;
            }
        }

        return nullHandling("Definition", pojoClass);
    }

    @Override
    public<T> PropertySetAdapter<T> getPropertySetAdapter(final Class<?> pojoClass) {
        final Class<?> clazz = handleBindableProxyClass( pojoClass );
        for (PropertySetAdapter adapter : propertySetAdapters) {
            if ( adapter.accepts( clazz ) ) {
                return adapter;
            }
        }

        return nullHandling("Property Set", pojoClass);
    }

    @Override
    public<T> PropertyAdapter<T> getPropertyAdapter(final Class<?> pojoClass) {
        final Class<?> clazz = handleBindableProxyClass( pojoClass );
        for (PropertyAdapter adapter : propertyAdapters) {
            if ( adapter.accepts( clazz ) ) {
                return adapter;
            }
        }

        return nullHandling("Property", pojoClass);
    }

    private Class<?> handleBindableProxyClass(final Class<?> pojoClass) {
        return BindableAdapterUtils.handleBindableProxyClass( pojoClass );
    }

    public static <T extends Adapter> void sortAdapters(List<T> adapters) {
        Collections.sort(adapters, (o1, o2) -> o1.getPriority() - o2.getPriority());
    }
    
    private <T> T nullHandling(String domain, Class<?> pojoClass) {
        final String message = "No " + domain + " adapter found for pojo with class [" + pojoClass.getName() + "]";
        LOGGER.severe( message );
        throw new NullPointerException( message );
    }

}

