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

package org.wirez.core.api.definition.adapter.binding;

import org.wirez.core.api.definition.adapter.PropertyAdapter;
import org.wirez.core.api.definition.property.PropertyType;

import java.util.Map;

public abstract class BindablePropertyAdapter<T> extends AbstractBindableAdapter<T> implements PropertyAdapter<T> {

    protected abstract Map<Class, String> getPropertyTypeFieldNames();

    protected abstract Map<Class, String> getPropertyCaptionFieldNames();

    protected abstract Map<Class, String> getPropertyDescriptionFieldNames();

    protected abstract Map<Class, String> getPropertyReadOnlyFieldNames();

    protected abstract Map<Class, String> getPropertyOptionalFieldNames();
    
    protected abstract Map<Class, String> getPropertyValueFieldNames();

    protected abstract Map<Class, String> getPropertyDefaultValueFieldNames();

    @Override
    public String getId(final T pojo) {
        return BindableAdapterUtils.getPropertyId( pojo.getClass() );
    }

    @Override
    public PropertyType getType(final T pojo) {
        final Class<?> clazz = BindableAdapterUtils.handleBindableProxyClass( pojo.getClass() );
        return getProxiedValue( pojo, getPropertyTypeFieldNames().get( clazz ) );
    }
    
    @Override
    public String getCaption(final T pojo) {
        final Class<?> clazz = BindableAdapterUtils.handleBindableProxyClass( pojo.getClass() );
        return getProxiedValue( pojo, getPropertyCaptionFieldNames().get( clazz ) );
    }

    @Override
    public String getDescription(final T pojo) {
        final Class<?> clazz = BindableAdapterUtils.handleBindableProxyClass( pojo.getClass() );
        return getProxiedValue( pojo, getPropertyDescriptionFieldNames().get( clazz ) );
    }

    @Override
    public boolean isReadOnly(final T pojo) {
        final Class<?> clazz = BindableAdapterUtils.handleBindableProxyClass( pojo.getClass() );
        return getProxiedValue( pojo, getPropertyReadOnlyFieldNames().get( clazz ) );
    }

    @Override
    public boolean isOptional(final T pojo) {
        final Class<?> clazz = BindableAdapterUtils.handleBindableProxyClass( pojo.getClass() );
        return getProxiedValue( pojo, getPropertyOptionalFieldNames().get( clazz ) );
    }

    @Override
    public Object getValue(final T pojo) {
        final Class<?> clazz = BindableAdapterUtils.handleBindableProxyClass( pojo.getClass() );
        return getProxiedValue( pojo, getPropertyValueFieldNames().get( clazz ) );
    }

    @Override
    public Object getDefaultValue(final T pojo) {
        final Class<?> clazz = BindableAdapterUtils.handleBindableProxyClass( pojo.getClass() );
        return getProxiedValue( pojo, getPropertyDefaultValueFieldNames().get( clazz ) );
    }

    @Override
    public void setValue(final T pojo, final Object value) {
        final Class<?> clazz = BindableAdapterUtils.handleBindableProxyClass( pojo.getClass() );
        setProxiedValue( pojo, getPropertyValueFieldNames().get( clazz ), value );
    }

    @Override
    public boolean accepts(final Class<?> pojoClass ) {
        final Class<?> clazz = BindableAdapterUtils.handleBindableProxyClass( pojoClass );
        return getPropertyValueFieldNames().containsKey(clazz);

    }

}
