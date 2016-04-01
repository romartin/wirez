package org.wirez.core.api.definition.adapter;

import org.wirez.core.api.graph.Graph;

import java.util.Set;

public interface DefinitionSetAdapter<T> extends Adapter<T> {

    String getId(T pojo);

    String getDomain(T pojo);

    Class<? extends Graph> getGraph(T pojo);

    String getGraphFactory(T pojo);

    String getDescription(T pojo);
    
    Set<String> getDefinitions(T pojo);

}
