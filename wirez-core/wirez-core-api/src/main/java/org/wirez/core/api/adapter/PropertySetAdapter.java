package org.wirez.core.api.adapter;

import org.wirez.core.api.definition.Definition;
import org.wirez.core.api.definition.property.Property;
import org.wirez.core.api.definition.property.PropertySet;

import java.util.Set;

public interface PropertySetAdapter<T extends PropertySet> extends Adapter<T> {

    Set<Property> getProperties(T pojo);
    
}
