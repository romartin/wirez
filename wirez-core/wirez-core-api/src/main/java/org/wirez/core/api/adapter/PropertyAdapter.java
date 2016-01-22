package org.wirez.core.api.adapter;

import org.wirez.core.api.definition.property.Property;
import org.wirez.core.api.definition.property.PropertySet;

import java.util.Set;

public interface PropertyAdapter<T extends Property> extends Adapter<T> {

    Object getValue(T pojo);
    
    void setValue(T pojo, Object value);
    
}
