package org.wirez.core.definition.adapter.binding;

import org.wirez.core.definition.adapter.DefinitionSetAdapter;

import java.util.Map;
import java.util.Set;

public interface BindableDefinitionSetAdapter<T> extends DefinitionSetAdapter<T> {
    
    void setBindings(Map<Class, String> propertyDescriptionFieldNames,
                     Map<Class, Class> graphFactoryType,
                     Set<String> definitionIds);
}
