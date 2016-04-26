package org.wirez.core.api.lookup.definition;

import java.util.Set;

public interface DefinitionRepresentation {

    /**
     * The definition identifier.
     */
    String getDefinitionId();

    /**
     * Returns true if the definition is a Node, otherwise only can be an edge.
     */
    boolean isNode();

    /**
     * The collection of labels.
     */
    Set<String> getLabels();
    
}
