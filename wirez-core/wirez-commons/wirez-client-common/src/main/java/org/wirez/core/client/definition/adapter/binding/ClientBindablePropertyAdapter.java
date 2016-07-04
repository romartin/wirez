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

package org.wirez.core.client.definition.adapter.binding;

import org.wirez.core.definition.adapter.binding.BindableAdapterUtils;
import org.wirez.core.definition.adapter.binding.BindablePropertyAdapter;
import org.wirez.core.definition.property.PropertyType;

import java.util.LinkedHashMap;
import java.util.Map;

class ClientBindablePropertyAdapter extends AbstractClientBindableAdapter<Object> implements BindablePropertyAdapter<Object, Object> {

    private Map<Class, String> propertyTypeFieldNames;
    private Map<Class, String> propertyCaptionFieldNames;
    private Map<Class, String> propertyDescriptionFieldNames;
    private Map<Class, String> propertyReadOnlyFieldNames;
    private Map<Class, String> propertyOptionalFieldNames;
    private Map<Class, String> propertyValueFieldNames;
    private Map<Class, String> propertyDefaultValueFieldNames;
    private Map<Class, String> propertyAllowedValuesFieldNames;

    @Override
    public void setBindings( final Map<Class, String> propertyTypeFieldNames,
                             final Map<Class, String> propertyCaptionFieldNames,
                             final Map<Class, String> propertyDescriptionFieldNames,
                             final Map<Class, String> propertyReadOnlyFieldNames,
                             final Map<Class, String> propertyOptionalFieldNames,
                             final Map<Class, String> propertyValueFieldNames,
                             final Map<Class, String> propertyDefaultValueFieldNames,
                             final Map<Class, String> propertyAllowedValuesFieldNames ) {
        this.propertyTypeFieldNames = propertyTypeFieldNames;
        this.propertyCaptionFieldNames = propertyCaptionFieldNames;
        this.propertyDescriptionFieldNames = propertyDescriptionFieldNames;
        this.propertyReadOnlyFieldNames = propertyReadOnlyFieldNames;
        this.propertyOptionalFieldNames = propertyOptionalFieldNames;
        this.propertyValueFieldNames = propertyValueFieldNames;
        this.propertyDefaultValueFieldNames = propertyDefaultValueFieldNames;
        this.propertyAllowedValuesFieldNames = propertyAllowedValuesFieldNames;
    }

    @Override
    public String getId(final Object pojo) {
        return BindableAdapterUtils.getPropertyId( pojo.getClass() );
    }

    @Override
    public PropertyType getType(final Object pojo) {
        final Class<?> clazz = BindableAdapterUtils.handleBindableProxyClass( pojo.getClass() );
        return getProxiedValue( pojo, getPropertyTypeFieldNames().get( clazz ) );
    }
    
    @Override
    public String getCaption(final Object pojo) {
        final Class<?> clazz = BindableAdapterUtils.handleBindableProxyClass( pojo.getClass() );
        return getProxiedValue( pojo, getPropertyCaptionFieldNames().get( clazz ) );
    }

    @Override
    public String getDescription(final Object pojo) {
        final Class<?> clazz = BindableAdapterUtils.handleBindableProxyClass( pojo.getClass() );
        return getProxiedValue( pojo, getPropertyDescriptionFieldNames().get( clazz ) );
    }

    @Override
    public boolean isReadOnly(final Object pojo) {
        final Class<?> clazz = BindableAdapterUtils.handleBindableProxyClass( pojo.getClass() );
        return getProxiedValue( pojo, getPropertyReadOnlyFieldNames().get( clazz ) );
    }

    @Override
    public boolean isOptional(final Object pojo) {
        final Class<?> clazz = BindableAdapterUtils.handleBindableProxyClass( pojo.getClass() );
        return getProxiedValue( pojo, getPropertyOptionalFieldNames().get( clazz ) );
    }

    @Override
    public Object getValue(final Object pojo) {
        final Class<?> clazz = BindableAdapterUtils.handleBindableProxyClass( pojo.getClass() );
        return getProxiedValue( pojo, getPropertyValueFieldNames().get( clazz ) );
    }

    @Override
    public Object getDefaultValue(final Object pojo) {
        final Class<?> clazz = BindableAdapterUtils.handleBindableProxyClass( pojo.getClass() );
        return getProxiedValue( pojo, getPropertyDefaultValueFieldNames().get( clazz ) );
    }

    @Override
    public void setValue(final Object pojo, final Object value) {

        if ( isReadOnly( pojo ) ) {

            throw new RuntimeException( "Cannot set new value for property [" + getId( pojo ) + "] as it is read only! " );

        }

        final Class<?> clazz = BindableAdapterUtils.handleBindableProxyClass( pojo.getClass() );
        
        setProxiedValue( pojo, getPropertyValueFieldNames().get( clazz ), value );
        
    }

    @Override
    public Map<Object, String> getAllowedValues( final Object pojo ) {
        
        final Class<?> clazz = BindableAdapterUtils.handleBindableProxyClass( pojo.getClass() );
        
        final Iterable<Object> result = getProxiedValue( pojo, getPropertyAllowedValuesFieldNames().get( clazz ) );
        
        if ( null != result ) {
            
            final Map<Object, String> allowedValues = new LinkedHashMap<>();
            
            for ( final Object o : result ) {
                
                allowedValues.put( o, o.toString() );
                
            }
            
            return allowedValues;
            
        }
        
        return null;
    }

    @Override
    public boolean accepts(final Class<?> pojoClass ) {
        if ( null != propertyValueFieldNames ) {
            final Class<?> clazz = BindableAdapterUtils.handleBindableProxyClass( pojoClass );
            return getPropertyValueFieldNames().containsKey(clazz);
        }
        return false;
    }

    private Map<Class, String> getPropertyTypeFieldNames() {
        return propertyTypeFieldNames;
    }

    private Map<Class, String> getPropertyCaptionFieldNames() {
        return propertyCaptionFieldNames;
    }

    private Map<Class, String> getPropertyDescriptionFieldNames() {
        return propertyDescriptionFieldNames;
    }

    private Map<Class, String> getPropertyReadOnlyFieldNames() {
        return propertyReadOnlyFieldNames;
    }

    private Map<Class, String> getPropertyOptionalFieldNames() {
        return propertyOptionalFieldNames;
    }

    private Map<Class, String> getPropertyValueFieldNames() {
        return propertyValueFieldNames;
    }

    private Map<Class, String> getPropertyDefaultValueFieldNames() {
        return propertyDefaultValueFieldNames;
    }

    private Map<Class, String> getPropertyAllowedValuesFieldNames() {
        return propertyAllowedValuesFieldNames;
    }
    
}
