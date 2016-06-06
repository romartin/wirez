package org.wirez.core.definition.adapter.binding;

import org.wirez.core.api.FactoryManager;

import java.util.*;

public abstract class AbstractBindableMorphAdapter<S, T> implements BindableMorphAdapter<S, T> {

    protected Class<?> definitionType;
    protected Map<Class<?>, Collection<Class<?>>> domainMorphs;

    FactoryManager factoryManager;

    public AbstractBindableMorphAdapter(final FactoryManager factoryManager) {
        this.factoryManager = factoryManager;
    }

    protected abstract T doMerge( S source, T result );

    @Override
    public BindableMorphAdapter<S, T> setDefaultDefinitionType(final Class<?> type) {
        this.definitionType = type;
        return this;
    }

    @Override
    public BindableMorphAdapter<S, T> setDomainMorphs(final Map<Class<?>, Collection<Class<?>>> domainMorphs) {
        this.domainMorphs = domainMorphs;
        return this;
    }

    @Override
    public boolean canMorph( final String definitionId ) {
        
        Set<Class<?>> s = getDomainMorphs().keySet();
        
        for ( Class<?> c : s ) {
            
            if ( getDefinitionId( c ).equals( definitionId ) ) {
                
                return true;
                
            }
            
        }
        
        return false;
    }
    
    public boolean canMorphType( final Class<?> type ) {
        return getDomainMorphs().keySet().contains( type );
    }

    @Override
    public String getDefaultDefinition() {
        return getDefinitionId( getDefaultDefinitionClass() );
    }

    @Override
    public T morph( final S source, 
                    final String target ) {

        if ( null == source ) {

            throw new IllegalArgumentException( "Cannot morph from unspecified source." );

        }

        if ( null == target ) {

            throw new IllegalArgumentException( "Cannot morph to unspecified target." );

        }

        final Class<?> targetClass =  getMorphTargetClass( source.getClass(), target );

        return doMorph( source, targetClass );

    }

    protected T doMorph( final S source,
                         final Class<?> morphTarget ) {

        final String id = getDefinitionId( morphTarget );

        final T result = factoryManager.newDomainObject( id );

        if ( null != result ) {

            doMerge( source, result );

            return result;

        }

        return null;

    }

    @Override
    public Collection<String> getMorphTargets( final S source ) {
        
        return getMorphTargetsForType( source.getClass() );

    }

    protected Collection<String> getMorphTargetsForType( final Class<?> sourceType ) {

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

    @Override
    public boolean accepts( final Class<?> type ) {
        return isThisInstanceValid() && type != null && getDomainMorphs().keySet().contains( type );
    }

    protected Class<?> getMorphTargetClass( final Class<?> sourceType, final String target ) {

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

    protected Map<Class<?>, Collection<Class<?>>> getDomainMorphs() {
        return domainMorphs;
    }

    protected Class<?> getDefaultDefinitionClass() {
        return definitionType;
    }

    protected String getDefinitionId( final Class<?> definitionClass ) {
        return BindableAdapterUtils.getDefinitionId( definitionClass );
    }

    // TODO: Refactor this. This is due as the DefinitionManager is injecting instances of BindableMorphAdapters
    // that are only used by the AdapterProxy classes and should be not visible ( use package protected class
    // modifiers, just BindableAdapterFactory is visible ) but they are being eligible and injected into the DefinitionManager.
    // So calling DefinitionManager#getMorphAdapter is returning instances that are not being configured ( method
    // setDomainMorphs not baccled by the parent adapter proxy instance ).
    protected boolean isThisInstanceValid() {
        return null != domainMorphs;
    }

}
