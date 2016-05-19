package org.wirez.core.definition.adapter.binding;

import org.wirez.core.definition.adapter.DefinitionAdapter;
import org.wirez.core.definition.util.DefinitionUtils;
import org.wirez.core.graph.Element;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class BindableDefinitionAdapter<T> extends AbstractBindableAdapter<T> implements DefinitionAdapter<T> {

    protected DefinitionUtils definitionUtils;

    public BindableDefinitionAdapter( final DefinitionUtils definitionUtils ) {
        this.definitionUtils = definitionUtils;
    }

    protected abstract Class<?> getNamePropertyClass();
    protected abstract Map<Class, Set<String>> getPropertySetsFieldNames();
    protected abstract Map<Class, Set<String>> getPropertiesFieldNames();
    protected abstract Map<Class, Class> getPropertyGraphElementFieldNames();
    protected abstract Map<Class, String> getPropertyElementFactoryFieldNames();
    protected abstract Map<Class, String> getPropertyLabelsFieldNames();
    protected abstract Map<Class, String> getPropertyTitleFieldNames();
    protected abstract Map<Class, String> getPropertyCategoryFieldNames();
    protected abstract Map<Class, String> getPropertyDescriptionFieldNames();
    
    @Override
    public String getId(final T pojo) {
        return BindableAdapterUtils.getDefinitionId( pojo.getClass() );
    }

    @Override
    public Object getNameProperty(final T pojo) {
        
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

    @Override
    public String getCategory(final T pojo) {
        final Class<?> clazz = BindableAdapterUtils.handleBindableProxyClass( pojo.getClass() );
        return getProxiedValue( pojo, getPropertyCategoryFieldNames().get( clazz ) );
    }

    @Override
    public String getTitle(final T pojo) {
        final Class<?> clazz = BindableAdapterUtils.handleBindableProxyClass( pojo.getClass() );
        return getProxiedValue( pojo, getPropertyTitleFieldNames().get( clazz ) );
    }

    @Override
    public String getDescription(final T pojo) {
        final Class<?> clazz = BindableAdapterUtils.handleBindableProxyClass( pojo.getClass() );
        return getProxiedValue( pojo, getPropertyDescriptionFieldNames().get( clazz ) );
    }

    @Override
    public Set<String> getLabels(final T pojo) {
        final Class<?> clazz = BindableAdapterUtils.handleBindableProxyClass( pojo.getClass() );
        return getProxiedValue( pojo, getPropertyLabelsFieldNames().get( clazz ) );
    }

    @Override
    public Set<?> getPropertySets(final T pojo) {
        final Class<?> clazz = BindableAdapterUtils.handleBindableProxyClass( pojo.getClass() );
        return getProxiedSet( pojo, getPropertySetsFieldNames().get( clazz ) );
    }

    @Override
    public Set<?> getProperties(final T pojo) {
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

    @Override
    public Class<? extends Element> getGraphElement(final T pojo) {
        final Class<?> clazz = BindableAdapterUtils.handleBindableProxyClass( pojo.getClass() );
        return getPropertyGraphElementFieldNames().get( clazz );
    }

    @Override
    public String getElementFactory(final T pojo) {
        final Class<?> clazz = BindableAdapterUtils.handleBindableProxyClass( pojo.getClass() );
        return getPropertyElementFactoryFieldNames().get( clazz );
    }

    @Override
    public boolean accepts(final Class<?> pojoClass ) {
        final Class<?> clazz = BindableAdapterUtils.handleBindableProxyClass( pojoClass );
        return getPropertyCategoryFieldNames().containsKey( clazz );
    }

}
