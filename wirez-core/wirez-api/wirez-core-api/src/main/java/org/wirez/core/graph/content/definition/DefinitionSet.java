package org.wirez.core.graph.content.definition;

import org.wirez.core.graph.content.HasBounds;

/**
 * Content for a Graph's definition set. 
 * It just provides the definition set identifier, as it's an application scoped data object.
 */
public interface DefinitionSet extends Definition<String>, HasBounds {

}
