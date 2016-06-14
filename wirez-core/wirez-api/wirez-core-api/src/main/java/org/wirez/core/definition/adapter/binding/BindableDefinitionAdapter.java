package org.wirez.core.definition.adapter.binding;

import org.wirez.core.definition.adapter.DefinitionAdapter;

import java.util.Map;
import java.util.Set;

public interface BindableDefinitionAdapter<T> extends DefinitionAdapter<T>, HasInheritance {
    
    void setBindings(Class<?> namePropertyClass,
                     Map<Class, Class> baseTypes,
                     Map<Class, Set<String>> propertySetsFieldNames,
                     Map<Class, Set<String>> propertiesFieldNames,
                     Map<Class, Class> propertyGraphElementFieldNames,
                     Map<Class, String> propertyElementFactoryFieldNames,
                     Map<Class, String> propertyLabelsFieldNames,
                     Map<Class, String> propertyTitleFieldNames,
                     Map<Class, String> propertyCategoryFieldNames,
                     Map<Class, String> propertyDescriptionFieldNames);


}
