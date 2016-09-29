package org.kie.workbench.common.stunner.core.definition.adapter.binding;

import org.kie.workbench.common.stunner.core.definition.adapter.PropertySetAdapter;

import java.util.Map;
import java.util.Set;

public interface BindablePropertySetAdapter<T> extends PropertySetAdapter<T> {
    
    void setBindings(Map<Class, String> propertyNameFieldNames,
                     Map<Class, Set<String>> propertiesFieldNames);
    
}
