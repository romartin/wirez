package org.wirez.core.client.definition.adapter.binding;

import org.wirez.core.definition.adapter.binding.BindableAdapterUtils;
import org.wirez.core.definition.adapter.binding.BindableDefinitionAdapter;
import org.wirez.core.definition.util.DefinitionUtils;
import org.wirez.core.graph.Element;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

class BindableDefinitionAdapterImpl extends AbstractBindableAdapter<Object> implements BindableDefinitionAdapter<Object> {

    private DefinitionUtils definitionUtils;

    private Class<?> namePropertyClass;
    private Map<Class, Set<String>> propertySetsFieldNames;
    private Map<Class, Set<String>> propertiesFieldNames;
    private Map<Class, Class> propertyGraphElementFieldNames;
    private Map<Class, String> propertyElementFactoryFieldNames;
    private Map<Class, String> propertyLabelsFieldNames;
    private Map<Class, String> propertyTitleFieldNames;
    private Map<Class, String> propertyCategoryFieldNames;
    private Map<Class, String> propertyDescriptionFieldNames;
    
    BindableDefinitionAdapterImpl(final DefinitionUtils definitionUtils ) {
        this.definitionUtils = definitionUtils;
    }

    @Override
    public void setBindings( final Class<?> namePropertyClass,
                            final Map<Class, Set<String>> propertySetsFieldNames,
                            final Map<Class, Set<String>> propertiesFieldNames,
                            final Map<Class, Class> propertyGraphElementFieldNames,
                            final Map<Class, String> propertyElementFactoryFieldNames,
                            final Map<Class, String> propertyLabelsFieldNames,
                            final Map<Class, String> propertyTitleFieldNames,
                            final Map<Class, String> propertyCategoryFieldNames,
                            final Map<Class, String> propertyDescriptionFieldNames) {
        this.namePropertyClass = namePropertyClass;
        this.propertySetsFieldNames = propertySetsFieldNames;
        this.propertiesFieldNames = propertiesFieldNames;
        this.propertyGraphElementFieldNames = propertyGraphElementFieldNames;
        this.propertyElementFactoryFieldNames = propertyElementFactoryFieldNames;
        this.propertyLabelsFieldNames = propertyLabelsFieldNames;
        this.propertyTitleFieldNames = propertyTitleFieldNames;
        this.propertyCategoryFieldNames = propertyCategoryFieldNames;
        this.propertyDescriptionFieldNames = propertyDescriptionFieldNames;
    }
    
            
    public String getId(final Object pojo) {
        return BindableAdapterUtils.getDefinitionId( pojo.getClass() );
    }

    public Object getNameProperty(final Object pojo) {

        final Set<?> properties = getProperties( pojo );
        if ( null != properties && !properties.isEmpty() ) {
            for (final Object property : properties) {
                if ( getNamePropertyClass().equals( property.getClass() )) {
                    return property;
                }
            }
        }
        return null;
    }

    public String getCategory(final Object pojo) {
        final Class<?> clazz = BindableAdapterUtils.handleBindableProxyClass( pojo.getClass() );
        return getProxiedValue( pojo, getPropertyCategoryFieldNames().get( clazz ) );
    }

    public String getTitle(final Object pojo) {
        final Class<?> clazz = BindableAdapterUtils.handleBindableProxyClass( pojo.getClass() );
        return getProxiedValue( pojo, getPropertyTitleFieldNames().get( clazz ) );
    }

    public String getDescription(final Object pojo) {
        final Class<?> clazz = BindableAdapterUtils.handleBindableProxyClass( pojo.getClass() );
        return getProxiedValue( pojo, getPropertyDescriptionFieldNames().get( clazz ) );
    }

    public Set<String> getLabels(final Object pojo) {
        final Class<?> clazz = BindableAdapterUtils.handleBindableProxyClass( pojo.getClass() );
        final String fName = getPropertyLabelsFieldNames().get( clazz );
        return getProxiedValue( pojo, fName );
    }

    public Set<?> getPropertySets(final Object pojo) {
        final Class<?> clazz = BindableAdapterUtils.handleBindableProxyClass( pojo.getClass() );
        return getProxiedSet( pojo, getPropertySetsFieldNames().get( clazz ) );
    }

    public Set<?> getProperties(final Object pojo) {
        final Set<Object> result = new HashSet<>();

        // Obtain all properties from property sets.
        final Set<?> propertySetProperties = definitionUtils.getPropertiesFromPropertySets( pojo );
        if ( null != propertySetProperties ) {
            result.addAll( propertySetProperties );
        }

        // Find annotated runtime properties on the pojo.
        final Class<?> clazz = BindableAdapterUtils.handleBindableProxyClass( pojo.getClass() );
        Set<?> proxiedProps = getProxiedSet( pojo, getPropertiesFieldNames().get( clazz) );
        if ( null != proxiedProps ) {
            result.addAll( proxiedProps );
        }

        return result;
    }

    public Class<? extends Element> getGraphElement(final Object pojo) {
        final Class<?> clazz = BindableAdapterUtils.handleBindableProxyClass( pojo.getClass() );
        return getPropertyGraphElementFieldNames().get( clazz );
    }

    public String getElementFactory(final Object pojo) {
        final Class<?> clazz = BindableAdapterUtils.handleBindableProxyClass( pojo.getClass() );
        return getPropertyElementFactoryFieldNames().get( clazz );
    }

    public boolean accepts(final Class<?> pojoClass ) {
        
        if ( null != namePropertyClass ) {
            final Class<?> clazz = BindableAdapterUtils.handleBindableProxyClass( pojoClass );
            return getPropertyCategoryFieldNames().containsKey( clazz );
        }
        
        return false;
    }

    private Class<?> getNamePropertyClass() {
        return namePropertyClass;
    }

    private Map<Class, Set<String>> getPropertySetsFieldNames() {
        return propertySetsFieldNames;
    }

    private Map<Class, Set<String>> getPropertiesFieldNames() {
        return propertiesFieldNames;
    }

    private Map<Class, Class> getPropertyGraphElementFieldNames() {
        return propertyGraphElementFieldNames;
    }
    
    private Map<Class, String> getPropertyElementFactoryFieldNames() {
        return propertyElementFactoryFieldNames;
    }
    
    private Map<Class, String> getPropertyLabelsFieldNames() {
        return propertyLabelsFieldNames;
    }
    
    private Map<Class, String> getPropertyTitleFieldNames() {
        return propertyTitleFieldNames;
    }
    
    private Map<Class, String> getPropertyCategoryFieldNames() {
        return propertyCategoryFieldNames;
    }
    
    private Map<Class, String> getPropertyDescriptionFieldNames() {
        return propertyDescriptionFieldNames;
    }
    
}
