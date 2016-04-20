package org.wirez.core.backend.definition.adapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wirez.core.api.definition.adapter.PropertyAdapter;
import org.wirez.core.api.definition.adapter.binding.BindableAdapterUtils;
import org.wirez.core.api.definition.annotation.property.Property;
import org.wirez.core.api.definition.property.PropertyType;

import javax.enterprise.context.Dependent;
import java.lang.reflect.Field;

@Dependent
public class AnnotatedPropertyAdapter<T> extends AbstractAnnotatedAdapter<T> implements PropertyAdapter<T> {

    private static final Logger LOG = LoggerFactory.getLogger(AnnotatedPropertyAdapter.class);

    @Override
    public String getId(T property) {
        return BindableAdapterUtils.getPropertyId(property.getClass());
    }

    @Override
    public PropertyType getType(T property) {
        try {
            return getAnnotatedFieldValue( property, org.wirez.core.api.definition.annotation.property.Type.class );
        } catch (Exception e) {
            LOG.error("Error obtaining annotated category for Property with id " + getId( property ));
        }

        return null;
    }

    @Override
    public String getCaption(T property) {
        try {
            return getAnnotatedFieldValue( property, org.wirez.core.api.definition.annotation.property.Caption.class );
        } catch (Exception e) {
            LOG.error("Error obtaining annotated category for Property with id " + getId( property ));
        }

        return null;
    }

    @Override
    public String getDescription(T property) {
        try {
            return getAnnotatedFieldValue( property, org.wirez.core.api.definition.annotation.Description.class );
        } catch (Exception e) {
            LOG.error("Error obtaining annotated category for Property with id " + getId( property ));
        }

        return null;
    }

    @Override
    public boolean isReadOnly(T property) {
        try {
            return getAnnotatedFieldValue( property, org.wirez.core.api.definition.annotation.property.ReadOnly.class );
        } catch (Exception e) {
            LOG.error("Error obtaining annotated category for Property with id " + getId( property ));
        }

        return false;
    }

    @Override
    public boolean isOptional(T property) {
        try {
            return getAnnotatedFieldValue( property, org.wirez.core.api.definition.annotation.property.Optional.class );
        } catch (Exception e) {
            LOG.error("Error obtaining annotated category for Property with id " + getId( property ));
        }

        return false;
    }

    @Override
    public Object getValue(T property) {

        Object result = null;

        if ( null != property ) {
            Field[] fields = property.getClass().getDeclaredFields();
            if ( null != fields ) {
                for (Field field : fields) {
                    org.wirez.core.api.definition.annotation.property.Value annotation = field.getAnnotation(org.wirez.core.api.definition.annotation.property.Value.class);
                    if ( null != annotation ) {
                        try {
                            return _getValue( field, annotation, property );
                        } catch (Exception e) {
                            LOG.error("Error obtaining annotated value for Property with id " + getId( property ), e);
                        }
                    }
                }
            }
        }
        
        return result;
        
    }

    @Override
    public Object getDefaultValue(T property) {

        Object result = null;

        if ( null != property ) {
            Field[] fields = property.getClass().getDeclaredFields();
            if ( null != fields ) {
                for (Field field : fields) {
                    org.wirez.core.api.definition.annotation.property.DefaultValue annotation = field.getAnnotation(org.wirez.core.api.definition.annotation.property.DefaultValue.class);
                    if ( null != annotation ) {
                        try {
                            return _getValue( field, annotation, property );
                        } catch (Exception e) {
                            LOG.error("Error obtaining annotated default value for T with id " + getId( property ));
                        }
                    }
                }
            }
        }

        return result;
        
    }

    @SuppressWarnings("unchecked")
    private <V> V _getValue( Field field, Object annotation, T property) throws IllegalAccessException {
        if ( null != annotation) {
            field.setAccessible(true);
            V result = (V) field.get(property);
            return result;
        }
        return null;
    }

    @Override
    public void setValue(final T property, final Object value) {

        if ( null != property ) {
            Field[] fields = property.getClass().getDeclaredFields();
            if ( null != fields ) {
                for (Field field : fields) {
                    org.wirez.core.api.definition.annotation.property.Value annotation = field.getAnnotation(org.wirez.core.api.definition.annotation.property.Value.class);
                    if ( null != annotation) {
                        try {
                            field.setAccessible(true);
                            field.set(property, value);
                        } catch (Exception e) {
                            LOG.error("Error setting value for T with id [" + getId( property ) + "] " +
                                    "and value [" + ( value != null ? value.toString() : "null" ) + "]");
                        }
                    }
                }
            }
        }
        
    }

    @Override
    public boolean accepts(Class<?> pojo) {
        return pojo.getAnnotation(Property.class) != null;
    }
    
    @Override
    public int getPriority() {
        return 100;
    }
    
}
