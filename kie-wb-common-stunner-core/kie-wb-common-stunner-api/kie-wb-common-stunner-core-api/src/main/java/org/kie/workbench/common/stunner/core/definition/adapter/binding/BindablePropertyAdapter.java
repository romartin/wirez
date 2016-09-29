package org.kie.workbench.common.stunner.core.definition.adapter.binding;

import org.kie.workbench.common.stunner.core.definition.adapter.PropertyAdapter;

import java.util.Map;

public interface BindablePropertyAdapter<T, V> extends PropertyAdapter<T, V> {

    void setBindings(Map<Class, String> propertyTypeFieldNames,
                     Map<Class, String> propertyCaptionFieldNames,
                     Map<Class, String> propertyDescriptionFieldNames,
                     Map<Class, String> propertyReadOnlyFieldNames,
                     Map<Class, String> propertyOptionalFieldNames,
                     Map<Class, String> propertyValueFieldNames,
                     Map<Class, String> propertyDefaultValueFieldNames,
                     Map<Class, String> propertyAllowedValuesFieldNames);
    
}
