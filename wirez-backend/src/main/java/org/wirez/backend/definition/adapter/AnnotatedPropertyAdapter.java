package org.wirez.backend.definition.adapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wirez.core.definition.adapter.PropertyAdapter;
import org.wirez.core.definition.adapter.binding.BindableAdapterUtils;
import org.wirez.core.definition.annotation.Description;
import org.wirez.core.definition.annotation.property.NameProperty;
import org.wirez.core.definition.annotation.property.*;
import org.wirez.core.definition.property.PropertyType;

import javax.enterprise.context.Dependent;
import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

@Dependent
public class AnnotatedPropertyAdapter<T> extends AbstractAnnotatedAdapter<T> implements PropertyAdapter<T, Object> {

    private static final Logger LOG = LoggerFactory.getLogger(AnnotatedPropertyAdapter.class);

    @Override
    public String getId(T property) {
        return BindableAdapterUtils.getPropertyId(property.getClass());
    }

    @Override
    public PropertyType getType(T property) {
        try {
            return getAnnotatedFieldValue( property, Type.class );
        } catch (Exception e) {
            LOG.error("Error obtaining annotated category for Property with id " + getId( property ));
        }

        return null;
    }

    @Override
    public String getCaption(T property) {
        try {
            return getAnnotatedFieldValue( property, Caption.class );
        } catch (Exception e) {
            LOG.error("Error obtaining annotated category for Property with id " + getId( property ));
        }

        return null;
    }

    @Override
    public String getDescription(T property) {
        try {
            return getAnnotatedFieldValue( property, Description.class );
        } catch (Exception e) {
            LOG.error("Error obtaining annotated category for Property with id " + getId( property ));
        }

        return null;
    }

    @Override
    public boolean isReadOnly(T property) {
        try {
            return getAnnotatedFieldValue( property, ReadOnly.class );
        } catch (Exception e) {
            LOG.error("Error obtaining annotated category for Property with id " + getId( property ));
        }

        return false;
    }

    @Override
    public boolean isOptional(T property) {
        try {
            return getAnnotatedFieldValue( property, Optional.class );
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
                    Value annotation = field.getAnnotation(Value.class);
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
    public Object getDefaultValue( T property ) {

        Object result = null;

        if ( null != property ) {
            Field[] fields = property.getClass().getDeclaredFields();
            if ( null != fields ) {
                for (Field field : fields) {
                    DefaultValue annotation = field.getAnnotation(DefaultValue.class);
                    if ( null != annotation ) {
                        try {
                            return _getValue( field, annotation, property );
                        } catch (Exception e) {
                            LOG.error("Error obtaining annotated default value for Property with id " + getId( property ));
                        }
                    }
                }
            }
        }

        return result;
        
    }

    @Override
    public Map<Object, String> getAllowedValues( T property ) {
        Map<Object, String> result = new LinkedHashMap<>();

        if ( null != property ) {
            
            Field[] fields = property.getClass().getDeclaredFields();
            
            if ( null != fields ) {
                
                for (Field field : fields) {

                    AllowedValues annotation = field.getAnnotation(AllowedValues.class);
                    
                    if ( null != annotation ) {
                        
                        try {

                            Iterable<?> value = _getValue( field, annotation, property );
                            
                            if ( null != value && value.iterator().hasNext() ) {

                                Iterator<?> vIt = value.iterator();
                                
                                while ( vIt.hasNext() ) {
                                    
                                    Object v = vIt.next();

                                    result.put( v, v.toString() );

                                }
                                
                                
                            }
                            
                        } catch (Exception e) {
                            LOG.error("Error obtaining annotated allowed values for Property with id " + getId( property ));
                        }
                        
                    }
                    
                }
                
            }
            
        }

        if ( !result.isEmpty() ) {

            return result;

        }
        
        return  null;
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
                    Value annotation = field.getAnnotation(Value.class);
                    if ( null != annotation) {
                        try {
                            field.setAccessible(true);
                            field.set(property, value);
                        } catch (Exception e) {
                            LOG.error("Error setting value for Property with id [" + getId( property ) + "] " +
                                    "and value [" + ( value != null ? value.toString() : "null" ) + "]");
                        }
                    }
                }
            }
        }
        
    }

    @Override
    public boolean accepts(Class<?> pojo) {
        return pojo.getAnnotation(Property.class) != null || pojo.getAnnotation(NameProperty.class) != null;
    }
    
    @Override
    public int getPriority() {
        return 100;
    }
    
}
