package org.wirez.core.definition.adapter;

import org.wirez.core.api.FactoryManager;
import org.wirez.core.definition.adapter.binding.BindableAdapterUtils;
import org.wirez.core.definition.adapter.binding.HasInheritance;
import org.wirez.core.definition.morph.MorphDefinition;
import org.wirez.core.definition.morph.MorphProperty;
import org.wirez.core.definition.util.DefinitionUtils;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

public abstract class BindableMorphAdapter<S> extends AbstractMorphAdapter<S> {

    public BindableMorphAdapter( DefinitionUtils definitionUtils,
                                 FactoryManager factoryManager ) {

        super( definitionUtils, factoryManager );

    }

    protected abstract <T> T doMerge( S source, T result );

    @Override
    @SuppressWarnings("unchecked")
    protected <T> T doMerge( final S source,
                             final MorphDefinition morphDefinition,
                             final T result ) {

        if ( definitionUtils.isNonePolicy( morphDefinition ) ) {

            return result;

        }

        if ( definitionUtils.isDefaultPolicy( morphDefinition ) ) {

            final Object nameProperty = getDefinitionManager().getDefinitionAdapter( source.getClass() ).getNameProperty( source );
            final Object namePropertyValue = getDefinitionManager().getPropertyAdapter( nameProperty.getClass() ).getValue( nameProperty );

            final Object targetNameProperty = getDefinitionManager().getDefinitionAdapter( result.getClass() ).getNameProperty( result );
            final PropertyAdapter<Object, Object> propertyAdapter =
                    (PropertyAdapter<Object, Object>) getDefinitionManager().getPropertyAdapter( targetNameProperty.getClass() );

            propertyAdapter.setValue( targetNameProperty, namePropertyValue );;

            return result;

        }

        return doMerge( source, result );

    }


    @Override
    public <T> Iterable<MorphProperty> getMorphProperties( final T definition ) {
        return getMorphPropertiesForType( definition.getClass() );
    }

    @Override
    public <T> Iterable<MorphDefinition> getMorphDefinitions( final T definition ) {
        return getMorphDefinitionsForType( definition.getClass() );
    }

    public Iterable<MorphDefinition> getMorphDefinitionsForType( final Class<?> type ) {
        final String dId = getDefinitionId( type );
        final String baseId = getBaseDefinitionId( type );
        return super.getMorphDefinitions( dId, baseId );
    }

    public <T> Iterable<MorphProperty> getMorphPropertiesForType( final Class<?> type ) {
        final String dId = getDefinitionId( type );
        final String baseId = getBaseDefinitionId( type );
        return super.getMorphProperties( dId, baseId );
    }

    public <T> Iterable<String> getTargetsForType( final Class<?> type ) {
        final String dId = getDefinitionId( type );
        final String baseId = getBaseDefinitionId( type );
        return getTargets( type, dId, baseId );

    }

    @Override
    protected Iterable<String> getTargets( final Class<?> type,
                                           final String definitionId,
                                           final String baseId) {

        final Iterable<String> superTargets = super.getTargets(type, definitionId, baseId);

        if ( null != superTargets && superTargets.iterator().hasNext() ) {

            final Set<String> result = new LinkedHashSet<>();

            for ( final String s : superTargets ) {

                final String[] types = getTypes( type, s );

                if ( null != types && types.length > 0 ) {

                    Collections.addAll(result, types);

                } else {

                    result.add( s );

                }

            }

            return result;

        }

        return null;
    }

    protected String[] getTypes( final Class<?> adapterType, String baseType ) {
        final DefinitionAdapter<Object> definitionAdapter = getDefinitionManager().getDefinitionAdapter( adapterType );
        if ( definitionAdapter instanceof HasInheritance) {
            return ( (HasInheritance) definitionAdapter ).getTypes( baseType );
        }
        return null;

    }

    @SuppressWarnings("unchecked")
    protected String getBaseDefinitionId( final Class<?> type ) {
        final DefinitionAdapter<Object> definitionAdapter = getDefinitionManager().getDefinitionAdapter( type );
        if ( definitionAdapter instanceof HasInheritance) {
            return ( (HasInheritance) definitionAdapter ).getBaseType( type );
        }
        return null;
    }

    protected String getDefinitionId( final Class<?> type ) {
        return BindableAdapterUtils.getDefinitionId( type );
    }

}
