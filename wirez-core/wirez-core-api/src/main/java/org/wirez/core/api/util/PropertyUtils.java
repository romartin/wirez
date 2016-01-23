package org.wirez.core.api.util;

import org.jboss.errai.databinding.client.HasProperties;
import org.jboss.errai.databinding.client.api.DataBinder;
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

}
