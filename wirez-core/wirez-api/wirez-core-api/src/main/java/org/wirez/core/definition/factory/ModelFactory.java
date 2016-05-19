package org.wirez.core.definition.factory;

public interface ModelFactory<W> {
    
    boolean accepts( String id );

    W build( String id );    
    
}
