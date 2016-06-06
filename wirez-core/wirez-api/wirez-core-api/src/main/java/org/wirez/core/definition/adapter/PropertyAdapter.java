package org.wirez.core.definition.adapter;

import org.wirez.core.definition.property.PropertyType;

import java.util.Map;

/**
 * A Property pojo adapter.. 
 */
public interface PropertyAdapter<T, V> extends PriorityAdapter {

    /**
     * Returns the property's identifier for a given pojo.
     */
    String getId(T pojo);

    /**
     * Returns the property's type for a given pojo.
     */
    PropertyType getType(T pojo);

    /**
     * Returns the property's caption for a given pojo.
     */
    String getCaption(T pojo);

    /**
     * Returns the property's description for a given pojo.
     */
    String getDescription(T pojo);

    /**
     * Specifies if the property is read only.
     */
    boolean isReadOnly(T pojo);

    /**
     * Specifies if the property is optional.
     */
    boolean isOptional(T pojo);

    /**
     * Returns the property's value for a given pojo.
     */
    V getValue(T pojo);

    /**
     * Returns the property's default value for a given pojo.
     */
    V getDefaultValue(T pojo);

    /**
     * Returns allowed values for this property, if multiple. 
     * Otherwise returns null,.
     */
    Map<V, String> getAllowedValues(T pojo);

    /**
     * Update's the property value for a given pojo..
     */
    void setValue(T pojo, V value);
    
}
