package org.wirez.core.definition.adapter.binding;

import org.wirez.core.definition.adapter.MorphProperty;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public abstract class BindableMorphProperty<P> implements MorphProperty<P> {

    public abstract Class<?> getPropertyClass();

    public abstract Map<Object, Class<?>> getMorphTargetClasses();

    public abstract Object getValue( P property );
    
    @Override
    public String getProperty() {
        return getPropertyId( getPropertyClass() );
    }

    @Override
    public String getMorphTarget( final Object value ) {
        return getDefinitionId( getMorphTargetClasses().get( value ) );
    }

    @Override
    public Collection<String> getMorphTargets() {
        final Set<String> result = new LinkedHashSet<>();
        final Map<Object, Class<?>>  morphTargets = getMorphTargetClasses();
        for ( final Map.Entry<Object, Class<?>> entry : morphTargets.entrySet() ) {
            final String tId = getDefinitionId( entry.getValue() );
            result.add( tId );
        }
        
        return result;
    }

    protected String getPropertyId(final Class<?> type ) {
        return BindableAdapterUtils.getPropertyId( type );
    }

    protected String getDefinitionId( final Class<?> type ) {
        return BindableAdapterUtils.getDefinitionId( type );
    }
    
}
