package org.wirez.core.definition.adapter.binding;

import org.wirez.core.api.FactoryManager;
import org.wirez.core.definition.adapter.MorphProperty;

import java.util.*;

public abstract class AbstractBindableInheritanceMorphAdapter<S, T extends S, P> extends AbstractBindableMorphAdapter<S, T>
        implements BindableInheritanceMorphAdapter<S, T, P> {

    protected Map<Class<?>, MorphProperty<P>> propertyMorphs;

    public AbstractBindableInheritanceMorphAdapter(final FactoryManager factoryManager) {
        super(factoryManager);
    }

    @Override
    public BindableInheritanceMorphAdapter<S, T, P> setPropertyMorph(final Map<Class<?>, MorphProperty<P>> propertyMorphs) {
        this.propertyMorphs = propertyMorphs;
        return this;
    }

    protected Map<Class<?>, MorphProperty<P>> getPropertyMorph() {
        return propertyMorphs;
    }
    
    @Override
    protected Map<Class<?>, Collection<Class<?>>> getDomainMorphs() {
        
        if ( null != getPropertyMorph() && !getPropertyMorph().isEmpty() ) {
        
            final Map<Class<?>, Collection<Class<?>>> result = new LinkedHashMap<>();
            
            for ( final Map.Entry<Class<?>, MorphProperty<P>> entry : getPropertyMorph().entrySet() ) {
                
                final Class<?> sourceType = entry.getKey();
                
                final MorphProperty<P> pm = entry.getValue();
                
                final BindableMorphProperty<P> morphProperty = (BindableMorphProperty<P>) pm;

                Collection<Class<?>> targets = result.get( sourceType );
                
                if ( null == targets ) {
                    
                    targets = new LinkedList<>();
                    
                    result.put( sourceType, targets );
                }

                targets.addAll( morphProperty.getMorphTargetClasses().values() );
                
            }
            
            return result;
            
        }
        
        return null;
    }

    @Override
    protected Class<?> getMorphTargetClass(final Class<?> sourceType, 
                                           final String target) {
        
        final Class<?> baseType = getBaseType( sourceType );
        
        return super.getMorphTargetClass( baseType, target );
        
    }

    @Override
    @SuppressWarnings("unchecked")
    public MorphProperty<P> getMorphProperty(final Class<?> sourceType ) {

        if ( null != getPropertyMorph() && !getPropertyMorph().isEmpty() ) {

            final Class<?> baseType = getBaseType( sourceType );
            
            return getPropertyMorph().get( baseType );

        }

        return null;
        
    }

    @Override
    public Collection<String> getMorphTargets( final S source ) {
        
        final Class<?> baseType = getBaseType( source.getClass() );
        
        return super.getMorphTargetsForType( baseType );
        
    }

    @Override
    public boolean canMorphType(final Class<?> type) {
        final Class<?> baseType = getBaseType( type );
        return super.canMorphType( baseType );
    }

    @Override
    public boolean canMorph( final String definitionId ) {
        final Class<?> baseType = getBaseType( definitionId );
        return super.canMorphType( baseType );
    }

    @Override
    public Class<?> getBaseType( final Class<?> type ) {
        
        for ( final Map.Entry<Class<?>, Collection<Class<?>>> entry : getDomainMorphs().entrySet() ) {
            final Class<?> key = entry.getKey();
            final Collection<Class<?>> values = entry.getValue();
            for ( final Class<?> target : values ) {
                if ( target.getName().equals( type.getName() ) ) {
                    return key;
                }
            }
        }
        
        return null;
    }

    protected Class<?> getBaseType( final String id ) {
        for ( final Map.Entry<Class<?>, Collection<Class<?>>> entry : getDomainMorphs().entrySet() ) {
            final Class<?> key = entry.getKey();
            final Collection<Class<?>> values = entry.getValue();
            for ( final Class<?> target : values ) {
                final String tId = getDefinitionId( target );
                if ( tId.equals( id ) ) {
                    return key;
                }
            }
        }
        return null;
    }
    
    @Override
    public boolean accepts( final Class<?> type ) {

        if ( isThisInstanceValid() && null != type ) {

            final Class<?> baseType = getBaseType( type );

            return super.accepts( baseType );

        }

        return false;
    }
    
    protected Collection<String> toStringCollection( final Iterable<P> source ) {
        
        if ( null != source ) {
            
            final Set<String> result = new LinkedHashSet<>();
            
            for ( final Object def : source ) {
                result.add( getDefinitionId( def.getClass() ) );
            }
            
            return result;
            
        }
        
        return null;
    }

    // See parent javadoc.
    @Override
    protected boolean isThisInstanceValid() {
        return null != propertyMorphs;
    }
}
