package org.wirez.core.definition.adapter.binding;

import org.wirez.core.definition.adapter.PropertySetAdapter;

import java.util.Map;
import java.util.Set;

public interface BindablePropertySetAdapter<T> extends PropertySetAdapter<T> {
    
    void setBindings(Map<Class, String> propertyNameFieldNames,
                     Map<Class, Set<String>> propertiesFieldNames);
    
}
