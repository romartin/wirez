package org.kie.workbench.common.stunner.core.definition.adapter.binding;

import org.kie.workbench.common.stunner.core.definition.adapter.DefinitionSetAdapter;

import java.util.Map;
import java.util.Set;

public interface BindableDefinitionSetAdapter<T> extends DefinitionSetAdapter<T> {
    
    void setBindings(Map<Class, String> propertyDescriptionFieldNames,
                     Map<Class, Class> graphFactoryType,
                     Set<String> definitionIds);
}
