package org.wirez.core.definition.adapter;

/**
 * Morphing based on inheritance and given by property value criteria. 
 * Provides the properties that produce morphing and the morhping target for a given property at runtime.
 * 
 * @param <S> The source definition.
 * @param <T> The target definition after morphing.
 * @param <P> The property for which this morphing logic is based on.
 */
public interface InheritanceMorphAdapter<S, T extends S, P> extends MorphAdapter<S, T> {

    Class<?> getBaseType( Class<?> type );
    
    MorphProperty<P> getMorphProperty(Class<?> type );

}
