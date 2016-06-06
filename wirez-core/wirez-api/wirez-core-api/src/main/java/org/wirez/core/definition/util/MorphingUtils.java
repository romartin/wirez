package org.wirez.core.definition.util;

import org.wirez.core.api.DefinitionManager;
import org.wirez.core.definition.adapter.DefinitionAdapter;
import org.wirez.core.definition.adapter.MorphAdapter;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

@ApplicationScoped
public class MorphingUtils {

    DefinitionManager definitionManager;

    protected MorphingUtils() {
    }

    @Inject
    @SuppressWarnings("all")
    public MorphingUtils(DefinitionManager definitionManager) {
        this.definitionManager = definitionManager;
    }

    
    public <T> Iterable<String> getMorphTargets( final T source ) {

        if ( null != source ) {

            final Iterable<MorphAdapter<T, ?>> morphAdapters = getMorphAdapters( source );

            if ( null != morphAdapters && morphAdapters.iterator().hasNext() ) {

                final Set<String> result = new LinkedHashSet<>();

                for ( final MorphAdapter<T, ?> morphAdapter : morphAdapters ) {

                    result.addAll( (Collection<String>) morphAdapter.getMorphTargets( source ) );

                }
                
                final String sourceId = definitionManager
                        .getDefinitionAdapter( source.getClass() )
                        .getId( source );

                if ( result.contains( sourceId ) ) {
                    
                    result.remove( sourceId );
                    
                }
                
                return result;
            }

        }

        return null;
    }

    public <T> MorphAdapter<T, ?> getMorphAdapter( final T source, 
                                                   final String targetMorph ) {

        if ( null != source ) {

            final Iterable<MorphAdapter<T, ?>> morphAdapters = getMorphAdapters( source );

            if ( null != morphAdapters && morphAdapters.iterator().hasNext() ) {

                final Set<String> result = new LinkedHashSet<>();

                for ( final MorphAdapter<T, ?> morphAdapter : morphAdapters ) {

                    final Iterable<String> morphTargets = morphAdapter.getMorphTargets( source );

                    if ( contains( morphTargets, targetMorph ) ) {

                        return morphAdapter;

                    }

                }

            }

        }

        return null;
    }
    
    @SuppressWarnings("unchecked")
    protected <T> Iterable<MorphAdapter<T, ?>> getMorphAdapters( final T source ) {

        if ( null != source ) {

            final Iterable<MorphAdapter<T, ?>> morphAdapters = definitionManager.getMorphAdapters( source.getClass() );

            if ( null != morphAdapters && morphAdapters.iterator().hasNext() ) {

                final Set<MorphAdapter<T, ?>> result = new LinkedHashSet<>();

                for ( final MorphAdapter<T, ?> morphAdapter : morphAdapters ) {

                    final DefinitionAdapter<Object> definitionAdapter = definitionManager.getDefinitionAdapter( source.getClass() );
                    
                    final String dId = definitionAdapter.getId( source );
                    
                    if ( morphAdapter.canMorph( dId ) ) {
                        
                        result.add( morphAdapter );
                    }
                    
                }

                return result;

            }

        }

        return null;
        
    } 

    protected boolean contains( final Iterable<String> iterable ,
                                final String value ) {

        if ( null != iterable && iterable.iterator().hasNext() ) {

            for ( final String s  : iterable ) {

                if ( s.equals( value ) ) {

                    return true;

                }

            }

        }

        return false;
    }
    
}
