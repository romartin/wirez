package org.wirez.core.definition.morph;

import org.wirez.core.definition.adapter.binding.BindableAdapterUtils;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public abstract class BindableMorphProperty<P, V> implements MorphProperty<V> {

    public abstract Class<?> getPropertyClass();

    public abstract Map getMorphTargetClasses();

    public abstract V getValue( P property );
    
    @Override
    public String getProperty() {
        return getPropertyId( getPropertyClass() );
    }

    @Override
    public String getMorphTarget( final V value ) {
        return getDefinitionId( (Class<?>) getMorphTargetClasses().get( value ));
    }

    @Override
    @SuppressWarnings("unchecked")
    public Collection<String> getMorphTargets() {
        final Set<String> result = new LinkedHashSet<>();
        final Map morphTargets = getMorphTargetClasses();
        Set<Map.Entry> entries = morphTargets.entrySet();
        for ( final Map.Entry entry : entries ) {
            final String tId = getDefinitionId( (Class<?>) entry.getValue());
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
