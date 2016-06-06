package org.wirez.core.backend.definition.adapter.binding;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wirez.core.backend.definition.adapter.AbstractRuntimeAdapter;
import org.wirez.core.definition.adapter.binding.BindableAdapterUtils;
import org.wirez.core.definition.adapter.binding.BindablePropertyAdapter;
import org.wirez.core.definition.property.PropertyType;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;

class RuntimeBindablePropertyAdapter<T, V> extends AbstractRuntimeAdapter<T>
    implements BindablePropertyAdapter<T, V> {

    private static final Logger LOG = LoggerFactory.getLogger(RuntimeBindablePropertyAdapter.class);

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
    public String getId(T property) {
        return BindableAdapterUtils.getPropertyId( property.getClass() );
    }

    @Override
    public PropertyType getType(T property) {
        Class<?> type = property.getClass();
        try {
            return getFieldValue( property, propertyTypeFieldNames.get( type ) );
        } catch ( IllegalAccessException e ) {
            LOG.error("Error obtaining type for Property with id " + getId( property ));
        }

        return null;
    }

    @Override
    public String getCaption(T property) {
        Class<?> type = property.getClass();
        try {
            return getFieldValue( property, propertyCaptionFieldNames.get( type ) );
        } catch ( IllegalAccessException e ) {
            LOG.error("Error obtaining caption for Property with id " + getId( property ));
        }

        return null;
    }

    @Override
    public String getDescription(T property) {
        Class<?> type = property.getClass();
        try {
            return getFieldValue( property, propertyDescriptionFieldNames.get( type ) );
        } catch ( IllegalAccessException e ) {
            LOG.error("Error obtaining description for Property with id " + getId( property ));
        }

        return null;
    }

    @Override
    public boolean isReadOnly(T property) {
        Class<?> type = property.getClass();
        try {
            return getFieldValue( property, propertyReadOnlyFieldNames.get( type ) );
        } catch ( IllegalAccessException e ) {
            LOG.error("Error obtaining read only flag for Property with id " + getId( property ));
        }

        return false;
    }

    @Override
    public boolean isOptional(T property) {
        Class<?> type = property.getClass();
        try {
            return getFieldValue( property, propertyOptionalFieldNames.get( type ) );
        } catch ( IllegalAccessException e ) {
            LOG.error("Error obtaining optional flag for Property with id " + getId( property ));
        }

        return false;
    }

    @Override
    public V getValue(T property) {
        Class<?> type = property.getClass();
        try {
            return getFieldValue( property, propertyValueFieldNames.get( type ) );
        } catch ( IllegalAccessException e ) {
            LOG.error("Error obtaining value for Property with id " + getId( property ));
        }

        return null;
    }

    @Override
    public V getDefaultValue(T property) {
        Class<?> type = property.getClass();
        try {
            return getFieldValue( property, propertyDefaultValueFieldNames.get( type ) );
        } catch ( IllegalAccessException e ) {
            LOG.error("Error obtaining default value for Property with id " + getId( property ));
        }

        return null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Map<V, String> getAllowedValues(T property) {
        Class<?> type = property.getClass();
        String field = propertyAllowedValuesFieldNames.get( type );
        Iterable<?> allowedValues =  null;

        try {
            allowedValues = getFieldValue( property, field );
        } catch ( IllegalAccessException e ) {
            LOG.error("Error obtaining allowed values for Property with id " + getId( property ));
        }

        if ( null != allowedValues && allowedValues.iterator().hasNext() ) {

            Map<V, String> result = new LinkedHashMap<V, String>();

            for ( Object v : allowedValues ) {
                V allowedValue = (V) v;
                result.put( allowedValue, allowedValue.toString() );
            }

            return result;
        }

        return null;
    }

    @Override
    public void setValue( T property,
                          V value ) {
        Class<?> type = property.getClass();
        String fieldName = propertyValueFieldNames.get( type );
        Field field = null;
        try {
            field = getField( property, fieldName );
        } catch (IllegalAccessException e) {
            LOG.error("Error setting value for Property with id " + getId( property )
                    + ". Field [" + fieldName + "] not found for type [" + type.getName() + "]");
        }
        if ( null != field ) {
            try {
                field.setAccessible( true );
                field.set( property, value );
            } catch ( Exception e ) {
                LOG.error("Error setting value for Property with id [" + getId( property ) + "] " +
                        "and value [" + ( value != null ? value.toString() : "null" ) + "]");
            }
        }
    }

    @Override
    public boolean accepts(Class<?> type) {
        return null != propertyTypeFieldNames && propertyTypeFieldNames.containsKey( type );
    }

}
