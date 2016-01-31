package org.wirez.core.api.util;

import org.jboss.errai.databinding.client.HasProperties;
import org.jboss.errai.databinding.client.api.DataBinder;
import org.wirez.core.api.definition.property.HasValue;
import org.wirez.core.api.definition.property.Property;
import org.wirez.core.api.definition.property.PropertyType;
import org.wirez.core.api.definition.property.type.*;
import org.wirez.core.api.graph.Bounds;
import org.wirez.core.api.graph.Element;
import org.wirez.core.api.graph.content.ViewContent;

import java.util.Set;

public class ElementUtils {

    // TODO: Remove from here.
    public static Object parseValue(final Property property, String raw) {
        final PropertyType type = property.getType();
        
        if (StringType.name.equals(type.getName())) {
            return raw;
        }

        if (ColorType.name.equals(type.getName())) {
            return raw;
        }

        if (IntegerType.name.equals(type.getName())) {
            return Integer.parseInt(raw);
        }

        if (DoubleType.name.equals(type.getName())) {
            return Double.parseDouble(raw);
        }

        if (BooleanType.name.equals(type.getName())) {
            return Boolean.parseBoolean(raw);
        }

        throw new RuntimeException("Unsupported property type [" + type.getName() + "]");
    }
    
    public static Property getProperty(final Element element, final String id) {
        if ( null != element ) {
            return getProperty(element.getProperties(), id);
        }

        return null;
    }

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

    public static Property getPropertyIgnoreCase(final Set<Property> properties, final String id) {
        if ( null != id && null != properties ) {
            for (final Property property : properties) {
                if (property.getId().equalsIgnoreCase(id)) {
                    return property;
                }
            }
        }

        return null;
    }

    public static Double[] getPosition(final ViewContent element) {
        final Bounds.Bound ul = element.getBounds().getUpperLeft();
        final double x = ul.getX();
        final double y = ul.getY();
        return new Double[] { x, y };
    }

    public static Double[] getSize(final ViewContent element) {
        final Bounds.Bound ul = element.getBounds().getUpperLeft();
        final Bounds.Bound lr = element.getBounds().getLowerRight();
        final double w = lr.getX() - ul.getX();
        final double h = lr.getY() - ul.getY();
        return new Double[] { w, h };
    }

}
