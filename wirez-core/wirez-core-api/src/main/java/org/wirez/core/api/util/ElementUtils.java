package org.wirez.core.api.util;

import org.wirez.core.api.DefinitionManager;
import org.wirez.core.api.definition.adapter.DefinitionAdapter;
import org.wirez.core.api.definition.adapter.PropertyAdapter;
import org.wirez.core.api.definition.adapter.PropertySetAdapter;
import org.wirez.core.api.definition.property.PropertyType;
import org.wirez.core.api.definition.property.type.*;
import org.wirez.core.api.graph.Element;
import org.wirez.core.api.graph.content.view.BoundImpl;
import org.wirez.core.api.graph.content.view.Bounds;
import org.wirez.core.api.graph.content.view.BoundsImpl;
import org.wirez.core.api.graph.content.view.View;

import java.util.Set;

// TODO: Remove this class - avoid statics.
public class ElementUtils {

    public static Object parseValue(final PropertyAdapter adapter, final Object property, String raw) {
        final PropertyType type = adapter.getType( property );
        
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

    public static Object getProperty(final Element element, final Class<?> propertyClass) {
        return getProperty( element, propertyClass.getSimpleName() );
    }
    
    public static Object getProperty(final Element element, final String id) {
        if ( null != element ) {
            return getProperty(element.getProperties(), id);
        }

        return null;
    }

    public static Object getProperty(final Set<?> properties, final Class<?> propertyClass) {
        return getProperty(properties, propertyClass.getSimpleName());
    }

    public static Object getProperty(final Set<?> properties, final String id) {
        if ( null != id && null != properties ) {
            for (final Object property : properties) {
                // TODO: Use property adapters.
                String pId = property.getClass().getSimpleName();
                if (pId.equals(id)) {
                    return property;
                }
            }
        }

        return null;
    }

    /**
     * Returns all properties, the ones from property sets as wellm for a given Definition.
     */
    public static Set<?> getAllProperties(DefinitionManager definitionManager, Object definition ) {

        DefinitionAdapter definitionAdapter = definitionManager.getDefinitionAdapter( definition.getClass() );
        Set<Object> properties = definitionAdapter.getProperties(definition);

        // And properties on each definition's annotated PropertySet.
        Set<?> propertySets = definitionAdapter.getPropertySets(definition);
        if ( null != propertySets && !propertySets.isEmpty() ) {
            for (Object propertySet : propertySets) {
                PropertySetAdapter propertySetAdapter = definitionManager.getPropertySetAdapter(propertySet.getClass());
                Set<?> setProperties = propertySetAdapter.getProperties(propertySet);
                if( null != setProperties ) {
                    properties.addAll(setProperties);
                }
            }
        }

        return properties;
    }

    public static Double[] getPosition(final View element) {
        final Bounds.Bound ul = element.getBounds().getUpperLeft();
        final double x = ul.getX();
        final double y = ul.getY();
        return new Double[] { x, y };
    }

    public static Double[] getSize(final View element) {
        final Bounds.Bound ul = element.getBounds().getUpperLeft();
        final Bounds.Bound lr = element.getBounds().getLowerRight();
        final double w = lr.getX() - ul.getX();
        final double h = lr.getY() - ul.getY();
        return new Double[] { Math.abs(w), Math.abs(h) };
    }

    public static void updateBounds(final double radius, 
                                    final View element) {
        final Double[] coords = getPosition(element);
        updateBounds(coords[0], coords[1], radius, element);
    }

    public static void updateBounds(final double x, 
                                    final double y,
                                    final double radius,
                                    final View element) {
        updateBounds(x, y ,radius * 2, radius * 2, element);
    }
    
    public static void updateBounds(final double width, final double height, final View element) {
        final Double[] coords = getPosition(element);
        updateBounds(coords[0], coords[1], width, height, element);
    }


    public static void updateBounds(final double x, 
                                    final double y, 
                                    final double width, 
                                    final double height, 
                                    final View element) {

        final Bounds bounds = new BoundsImpl(
                new BoundImpl(x + width, y + height),
                new BoundImpl(x, y)
        );
        
        element.setBounds(bounds);
    }
    
}
