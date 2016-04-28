package org.wirez.core.api.definition.factory;

public interface ModelFactory<W> {
    
    boolean accepts( String id );

    W build( String id );    
    
}
