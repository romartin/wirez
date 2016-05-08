package org.wirez.core.api.definition.adapter;

import org.wirez.core.api.definition.property.PropertyType;

/**
 * A Property pojo adapter.. 
 */
public interface PropertyAdapter<T> extends Adapter<T> {

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
    Object getValue(T pojo);

    /**
     * Returns the property's default value for a given pojo.
     */
    Object getDefaultValue(T pojo);

    /**
     * Update's the property value for a given pojo..
     */
    void setValue(T pojo, Object value);
    
}
