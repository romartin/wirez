package org.wirez.core.definition.adapter.binding;

import org.wirez.core.definition.util.DefinitionUtils;
import org.wirez.core.graph.Element;

import java.util.*;

public abstract class AbstractBindableDefinitionAdapter<T> implements BindableDefinitionAdapter<T> {

    protected DefinitionUtils definitionUtils;

    protected Class<?> namePropertyClass;
    protected Map<Class, Class> baseTypes;
    protected Map<Class, Set<String>> propertySetsFieldNames;
    protected Map<Class, Set<String>> propertiesFieldNames;
    protected Map<Class, Class> propertyGraphElementFieldNames;
    protected Map<Class, String> propertyElementFactoryFieldNames;
    protected Map<Class, String> propertyLabelsFieldNames;
    protected Map<Class, String> propertyTitleFieldNames;
    protected Map<Class, String> propertyCategoryFieldNames;
    protected Map<Class, String> propertyDescriptionFieldNames;

    public AbstractBindableDefinitionAdapter( final DefinitionUtils definitionUtils ) {
        this.definitionUtils = definitionUtils;
    }

    protected abstract Set<?> getBindProperties(final T pojo);

    @Override
    public void setBindings( final Class<?> namePropertyClass,
                             final Map<Class, Class> baseTypes,
                            final Map<Class, Set<String>> propertySetsFieldNames,
                            final Map<Class, Set<String>> propertiesFieldNames,
                            final Map<Class, Class> propertyGraphElementFieldNames,
                            final Map<Class, String> propertyElementFactoryFieldNames,
                            final Map<Class, String> propertyLabelsFieldNames,
                            final Map<Class, String> propertyTitleFieldNames,
                            final Map<Class, String> propertyCategoryFieldNames,
                            final Map<Class, String> propertyDescriptionFieldNames) {
        this.namePropertyClass = namePropertyClass;
        this.baseTypes = baseTypes;
        this.propertySetsFieldNames = propertySetsFieldNames;
        this.propertiesFieldNames = propertiesFieldNames;
        this.propertyGraphElementFieldNames = propertyGraphElementFieldNames;
        this.propertyElementFactoryFieldNames = propertyElementFactoryFieldNames;
        this.propertyLabelsFieldNames = propertyLabelsFieldNames;
        this.propertyTitleFieldNames = propertyTitleFieldNames;
        this.propertyCategoryFieldNames = propertyCategoryFieldNames;
        this.propertyDescriptionFieldNames = propertyDescriptionFieldNames;
    }

    @Override
    @SuppressWarnings("unchecked")
    public String getBaseType( final Class<?> type ) {

        final Class<?> baseType = baseTypes.get( type );

        if ( null != baseType ) {

            return getDefinitionId( baseType );

        }

        return null;
    }

    @Override
    public String[] getTypes( final String baseType ) {

        List<String> result = new LinkedList<>();

        for ( Map.Entry<Class, Class> entry : baseTypes.entrySet() ) {

            final Class type = entry.getKey();
            final Class _baseType  = entry.getValue();
            final String _id = getDefinitionId( _baseType );

            if ( baseType.equals( _id ) ) {

                result.add( getDefinitionId( type ) );
            }

        }

        if ( !result.isEmpty() ) {

            return result.toArray( new String[ result.size() ] );

        }

        return null;
    }

    public String getId(final T pojo) {
        return getDefinitionId( pojo.getClass() );
    }

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

    public Set<?> getProperties(final T pojo) {
        final Set<Object> result = new HashSet<>();

        // Obtain all properties from property sets.
        final Set<?> propertySetProperties = definitionUtils.getPropertiesFromPropertySets( pojo );
        if ( null != propertySetProperties ) {
            result.addAll( propertySetProperties );
        }

        final Set<?> bindProperties = getBindProperties( pojo );
        if ( null != bindProperties && !bindProperties.isEmpty() ) {
            result.addAll( bindProperties );
        }

        return result;
    }

    protected String getDefinitionId( Class<?> type ) {
        return BindableAdapterUtils.getDefinitionId( type );
    }

    @SuppressWarnings("unchecked")
    public Class<? extends Element> getGraphElement(final T pojo) {
        final Class<?> clazz = BindableAdapterUtils.handleBindableProxyClass( pojo.getClass() );
        return getPropertyGraphElementFieldNames().get( clazz );
    }

    public String getElementFactory(final T pojo) {
        final Class<?> clazz = BindableAdapterUtils.handleBindableProxyClass( pojo.getClass() );
        return getPropertyElementFactoryFieldNames().get( clazz );
    }

    public boolean accepts(final Class<?> pojoClass ) {
        final Class<?> clazz = BindableAdapterUtils.handleBindableProxyClass(pojoClass);
        final boolean hasType = getPropertyCategoryFieldNames().containsKey(clazz);

        // If not types found, check if it's a super type.
        return hasType || baseTypes.values().contains(clazz);
    }

    protected Class<?> getNamePropertyClass() {
        return namePropertyClass;
    }

    protected Map<Class, Set<String>> getPropertySetsFieldNames() {
        return propertySetsFieldNames;
    }

    protected Map<Class, Set<String>> getPropertiesFieldNames() {
        return propertiesFieldNames;
    }

    protected Map<Class, Class> getPropertyGraphElementFieldNames() {
        return propertyGraphElementFieldNames;
    }
    
    protected Map<Class, String> getPropertyElementFactoryFieldNames() {
        return propertyElementFactoryFieldNames;
    }
    
    protected Map<Class, String> getPropertyLabelsFieldNames() {
        return propertyLabelsFieldNames;
    }
    
    protected Map<Class, String> getPropertyTitleFieldNames() {
        return propertyTitleFieldNames;
    }
    
    protected Map<Class, String> getPropertyCategoryFieldNames() {
        return propertyCategoryFieldNames;
    }
    
    protected Map<Class, String> getPropertyDescriptionFieldNames() {
        return propertyDescriptionFieldNames;
    }

    @Override
    public int getPriority() {
        return 0;
    }

}
