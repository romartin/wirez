package org.wirez.core.api.definition.adapter;

import org.wirez.core.api.definition.property.PropertyType;

public interface PropertyAdapter<T> extends Adapter<T> {

    String getId(T pojo);

    PropertyType getType(T pojo);

    String getCaption(T pojo);

    String getDescription(T pojo);

    boolean isReadOnly(T pojo);

    boolean isOptional(T pojo);
    
    Object getValue(T pojo);
    
    Object getDefaultValue(T pojo);
    
    void setValue(T pojo, Object value);
    
}
