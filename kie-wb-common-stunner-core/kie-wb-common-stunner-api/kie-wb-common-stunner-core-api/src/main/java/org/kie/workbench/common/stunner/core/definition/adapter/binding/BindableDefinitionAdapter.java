package org.kie.workbench.common.stunner.core.definition.adapter.binding;

import org.kie.workbench.common.stunner.core.definition.adapter.DefinitionAdapter;
import org.kie.workbench.common.stunner.core.factory.graph.ElementFactory;

import java.util.Map;
import java.util.Set;

public interface BindableDefinitionAdapter<T> extends DefinitionAdapter<T>, HasInheritance {
    
    void setBindings(Class<?> namePropertyClass,
                     Map<Class, Class> baseTypes,
                     Map<Class, Set<String>> propertySetsFieldNames,
                     Map<Class, Set<String>> propertiesFieldNames,
                     Map<Class, Class> propertyGraphFactoryFieldNames,
                     Map<Class, String> propertyLabelsFieldNames,
                     Map<Class, String> propertyTitleFieldNames,
                     Map<Class, String> propertyCategoryFieldNames,
                     Map<Class, String> propertyDescriptionFieldNames);

    Class<? extends ElementFactory> getGraphFactory( Class<?> type );

}
