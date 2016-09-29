package org.kie.workbench.common.stunner.core.definition.adapter;

import org.kie.workbench.common.stunner.core.factory.graph.ElementFactory;

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
    Class<? extends ElementFactory> getGraphFactoryType( T pojo);


}
