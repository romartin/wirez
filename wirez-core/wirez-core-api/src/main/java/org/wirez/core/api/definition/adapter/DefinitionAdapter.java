package org.wirez.core.api.definition.adapter;

import org.wirez.core.api.graph.Element;

import java.util.Set;

public interface DefinitionAdapter<T> extends Adapter<T> {

    String getId(T pojo);

    String getCategory(T pojo);

    String getTitle(T pojo);

    String getDescription(T pojo);

    Set<String> getLabels(T pojo);
    
    Set<?> getPropertySets(T pojo);

    Set<?> getProperties(T pojo);

    Class<? extends Element> getGraphElement(T pojo);

    String getElementFactory(T pojo);

}
