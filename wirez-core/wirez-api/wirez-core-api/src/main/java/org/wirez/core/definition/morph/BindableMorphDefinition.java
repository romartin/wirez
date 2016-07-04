package org.wirez.core.definition.morph;

import org.wirez.core.definition.adapter.binding.BindableAdapterUtils;

import java.util.*;

public abstract class BindableMorphDefinition implements MorphDefinition {

    protected abstract Class<?> getDefaultType();
    protected abstract Map<Class<?>, Collection<Class<?>>> getDomainMorphs();

    @Override
    public boolean accepts( final String definitionId ) {

        Set<Class<?>> s = getDomainMorphs().keySet();

        for ( Class<?> c : s ) {

            if ( getDefinitionId( c ).equals( definitionId ) ) {

                return true;

            }

        }

        return false;

    }

    @Override
    public String getBase() {
        final Class<?> baseType = getDomainMorphs().keySet().iterator().next();
        return getDefinitionId( baseType );
    }

    @Override
    public MorphPolicy getPolicy() {
        return MorphPolicy.DEFAULT;
    }

    public boolean canMorphType(final Class<?> type ) {
        return getDomainMorphs().keySet().contains( type );
    }

    @Override
    public String getDefault() {

        return getDefinitionId( getDefaultType() );

    }

    @Override
    public Iterable<String> getTargets( final String sourceId ) {

        final Class<?> sourceType = getSourceType( sourceId );

        if ( null != sourceType ) {

            return getTargetsForType( sourceType );

        }

        return null;
    }

    protected Class<?> getSourceType( final String definitionId ) {

        Set<Class<?>> s = getDomainMorphs().keySet();

        for ( Class<?> c : s ) {

            if ( getDefinitionId( c ).equals( definitionId ) ) {

                return c;

            }

        }

        return null;

    }

    protected Collection<String> getTargetsForType( final Class<?> sourceType ) {

        final Collection<Class<?>> targetClasses = getDomainMorphs().get( sourceType );

        if ( null != targetClasses && !targetClasses.isEmpty() ) {

            final List<String> result = new LinkedList<>();

            for ( final Class<?> targetClass : targetClasses ) {

                final String id = getDefinitionId( targetClass );
                result.add( id );
            }

            return result;
        }

        return null;
    }

    protected Class<?> getTargetClass( final Class<?> sourceType, final String target ) {

        final Collection<Class<?>> targetClasses = getDomainMorphs().get( sourceType );

        if ( null != targetClasses && !targetClasses.isEmpty() ) {

            for ( final Class<?> targetClass : targetClasses ) {

                final String id = getDefinitionId( targetClass );

                if ( id.equals( target ) ) {

                    return targetClass;

                }

            }

        }

        return null;

    }

    protected String getDefinitionId( final Class<?> definitionClass ) {
        return BindableAdapterUtils.getDefinitionId( definitionClass );
    }

}
