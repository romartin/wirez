package org.wirez.core.api.util;

import org.wirez.core.api.definition.property.HasValue;
import org.wirez.core.api.definition.property.Property;

import java.util.Set;

public class PropertyUtils {
    
    public static Property getProperty(final Set<Property> properties, final String id) {
        if ( null != id && null != properties ) {
            for (final Property property : properties) {
                if (property.getId().equals(id)) {
                    return property;
                }
            }
        }
        
        return null;
    }

    public static <T> T getValue(final Set<Property> properties, final String id) {
        
        Property property = getProperty(properties, id);
        return getValue(property);
        
    }

    public static <T> T getValue(Property property) {

        if ( null != property ) {

            if ( property instanceof HasValue) {
                return (T) ( (HasValue) property).getValue();
            }

        }

        return null;
    }

    public static <T> void setValue(final Set<Property> properties, final String id, final T value) {
        Property property = getProperty(properties, id);

        if ( null != property ) {

            if ( property instanceof HasValue ) {
                ( (HasValue<T>) property).setValue(value);
            }

        }

    }
    
}
