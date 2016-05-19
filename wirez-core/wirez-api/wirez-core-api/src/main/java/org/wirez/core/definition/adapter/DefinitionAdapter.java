package org.wirez.core.definition.adapter;

import org.wirez.core.graph.Element;

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
     * Returns the definition's name property for a given pojo.
     * Name is a build-in property supported by different components and widgets, it's a good idea returning your 
     * pojo name property instance here so you can use all the different features for it.
     * If no name for the definition, can return null.
     */
    Object getNameProperty(T pojo);
    
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
     * Returns all the definition's properties for a given pojo.
     * Must return the properties from the different 
     * definition's property sets as well.
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
