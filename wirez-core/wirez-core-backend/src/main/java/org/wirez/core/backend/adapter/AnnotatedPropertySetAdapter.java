package org.wirez.core.backend.adapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wirez.core.api.adapter.PropertySetAdapter;
import org.wirez.core.api.definition.property.DefaultPropertySet;
import org.wirez.core.api.definition.property.Property;
import org.wirez.core.api.definition.property.PropertySet;

import javax.enterprise.context.ApplicationScoped;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

@ApplicationScoped
public class AnnotatedPropertySetAdapter implements PropertySetAdapter<PropertySet> {

    private static final Logger LOG = LoggerFactory.getLogger(AnnotatedPropertySetAdapter.class);
    
    @Override
    public Set<Property> getProperties(PropertySet propertySet) {
        Set<Property> result = null;

        if ( null != propertySet ) {
            Method[] methods = propertySet.getClass().getMethods();
            if ( null != methods ) {
                result = new HashSet<>();
                for (Method method : methods) {
                    org.wirez.core.api.annotation.propertyset.Property annotation = method.getAnnotation(org.wirez.core.api.annotation.propertyset.Property.class);
                    if ( null != annotation) {
                        try {
                            Property property = (Property) method.invoke(propertySet);
                            result.add(property);
                        } catch (Exception e) {
                            LOG.error("Error obtaining annotated properties for PropertySet with id " + propertySet.getPropertySetId());
                        }
                    }
                }
            }
        }

        return result;
    }

    @Override
    public boolean accepts(Class pojoClass) {
        return !pojoClass.equals(DefaultPropertySet.class);
    }
    
}
