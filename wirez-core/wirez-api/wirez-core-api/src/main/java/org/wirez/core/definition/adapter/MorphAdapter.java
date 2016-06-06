package org.wirez.core.definition.adapter;

import java.util.Collection;

/**
 * The morphing adapter. 
 * Provides the potential target morph domain objects in which the source can be morphed.
 * 
 * @param <S> The source definition.
 */
public interface MorphAdapter<S, T> extends Adapter {

    boolean canMorph( String definitionId );
    
    String getDefaultDefinition();
    
    Collection<String> getMorphTargets( S source );

    T morph( S source, String target );

}
