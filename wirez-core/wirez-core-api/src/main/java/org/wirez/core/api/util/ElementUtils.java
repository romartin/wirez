package org.wirez.core.api.util;

import org.jboss.errai.databinding.client.HasProperties;
import org.jboss.errai.databinding.client.api.DataBinder;
import org.wirez.core.api.definition.property.HasValue;
import org.wirez.core.api.definition.property.Property;
import org.wirez.core.api.graph.Bounds;
import org.wirez.core.api.graph.Element;
import org.wirez.core.api.graph.content.ViewContent;

import java.util.Set;

public class ElementUtils {

    public static Property getProperty(final Element element, final String id) {
        if ( null != element ) {
            final Set<Property> properties = element.getProperties();
            if ( null != id && null != properties ) {
                for (final Property property : properties) {
                    if (property.getId().equals(id)) {
                        return property;
                    }
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
