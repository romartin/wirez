package org.wirez.core.definition.adapter;

import org.wirez.core.graph.Graph;

import java.util.Set;

/**
 * A Definition Set pojo adapter.. 
 */
public interface DefinitionSetAdapter<T> extends PriorityAdapter {

    /**
     * Returns the definition set's identifier for a given pojo.
     */
    String getId(T pojo);

    /**
     * Returns the definition set's domain for a given pojo.
     */
    String getDomain(T pojo);

    /**
     * Returns the definition set's description for a given pojo.
     */
    String getDescription(T pojo);

    /**
     * Returns the definition set's definitions for a given pojo.
     */
    Set<String> getDefinitions(T pojo);

    /**
     * Returns the definition set's graph class for a given pojo.
     */
    Class<? extends Graph> getGraph(T pojo);

    /**
     * Returns the definition set's graph factory for a given pojo.
     */
    String getGraphFactory(T pojo);

}
