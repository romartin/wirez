package org.wirez.core.backend.adapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wirez.core.api.adapter.PropertyAdapter;
import org.wirez.core.api.definition.DefaultDefinitionSet;
import org.wirez.core.api.definition.property.DefaultProperty;
import org.wirez.core.api.definition.property.Property;

import javax.enterprise.context.ApplicationScoped;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

@ApplicationScoped
public class AnnotatedPropertyAdapter implements PropertyAdapter<Property> {

    private static final Logger LOG = LoggerFactory.getLogger(AnnotatedPropertyAdapter.class);
    
    @Override
    public Object getValue(Property property) {

        Object result = null;

        if ( null != property ) {
            Field[] fields = property.getClass().getDeclaredFields();
            if ( null != fields ) {
                for (Field field : fields) {
                    org.wirez.core.api.annotation.property.Value annotation = field.getAnnotation(org.wirez.core.api.annotation.property.Value.class);
                    if ( null != annotation) {
                        try {
                            field.setAccessible(true);
                            result = field.get(property);
                        } catch (Exception e) {
                            LOG.error("Error obtaining annotated value for Property with id " + property.getId());
                        }
                    }
                }
            }
        }
        
        return result;
        
    }

    @Override
    public Object getDefaultValue(Property property) {

        Object result = null;

        if ( null != property ) {
            Field[] fields = property.getClass().getDeclaredFields();
            if ( null != fields ) {
                for (Field field : fields) {
                    org.wirez.core.api.annotation.property.DefaultValue annotation = field.getAnnotation(org.wirez.core.api.annotation.property.DefaultValue.class);
                    if ( null != annotation) {
                        try {
                            field.setAccessible(true);
                            result = field.get(property);
                        } catch (Exception e) {
                            LOG.error("Error obtaining annotated default value for Property with id " + property.getId());
                        }
                    }
                }
            }
        }

        return result;
        
    }

    @Override
    public void setValue(final Property property, final Object value) {

        if ( null != property ) {
            Field[] fields = property.getClass().getDeclaredFields();
            if ( null != fields ) {
                for (Field field : fields) {
                    org.wirez.core.api.annotation.property.Value annotation = field.getAnnotation(org.wirez.core.api.annotation.property.Value.class);
                    if ( null != annotation) {
                        try {
                            field.setAccessible(true);
                            field.set(property, value);
                        } catch (Exception e) {
                            LOG.error("Error setting value for Property with id [" + property.getId() + "] " +
                                    "and value [" + ( value != null ? value.toString() : "null" ) + "]");
                        }
                    }
                }
            }
        }
        
    }

    @Override
    public boolean accepts(Object pojo) {
        return ( ! ( pojo instanceof DefaultProperty) );
    }
    
    @Override
    public int getPriority() {
        return 100;
    }
    
}
