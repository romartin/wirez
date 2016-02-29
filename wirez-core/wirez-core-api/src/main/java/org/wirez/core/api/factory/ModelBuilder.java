package org.wirez.core.api.factory;

import org.wirez.core.api.definition.DefinitionSet;

public interface ModelBuilder<W> {
    
    boolean accepts( String id );

    W build(String id);    
    
}
