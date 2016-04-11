package org.wirez.core.api.definition.adapter;

import org.wirez.core.api.graph.Element;

import java.util.Set;

/**
 * A Definition pojo adapter.. 
 */
public interface DefinitionAdapter<T> extends Adapter<T> {

    /**
     * Returns the definition's identifier for a given pojo.
     */
    String getId(T pojo);

    /**
     * Returns the definition's category for a given pojo.
     */
    String getCategory(T pojo);

    /**
     * Returns the definition's title for a given pojo.
     */
    String getTitle(T pojo);

    /**
     * Returns the definition's description for a given pojo.
     */
    String getDescription(T pojo);

    /**
     * Returns the definition's labels for a given pojo.
     */
    Set<String> getLabels(T pojo);

    /**
     * Returns the definition's property sets for a given pojo.
     */
    Set<?> getPropertySets(T pojo);

    /**
     * Returns the definition's properties for a given pojo.
     */
    Set<?> getProperties(T pojo);

    /**
     * Returns the definition's graph element class for a given pojo.
     */
    Class<? extends Element> getGraphElement(T pojo);

    /**
     * Returns the definition's graph element factory for a given pojo.
     */
    String getElementFactory(T pojo);

}
