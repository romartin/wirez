package org.wirez.core.definition.adapter.binding;

import org.wirez.core.definition.adapter.InheritanceMorphAdapter;
import org.wirez.core.definition.adapter.MorphProperty;

import java.util.Collection;
import java.util.Map;

public abstract class InheritanceMorphAdapterProxy<S, T extends S, P> implements InheritanceMorphAdapter<S, T, P> {

    protected final BindableInheritanceMorphAdapter adapter;

    public InheritanceMorphAdapterProxy(final BindableMorphAdapterFactory morphAdapterProducer) {
        this.adapter = morphAdapterProducer.newBindableInheritanceMorphAdapter();
        init();
    }

    @SuppressWarnings("unchecked")
    protected void init() {
        adapter.setDefaultDefinitionType( getDefaultDefinitionType() );
        adapter.setPropertyMorph( getPropertyMorphs() );
    }

    protected abstract Class<?> getDefaultDefinitionType();

    protected abstract Map<Class<?>, MorphProperty<P>> getPropertyMorphs();

    @Override
    @SuppressWarnings("unchecked")
    public Class<?> getBaseType(final Class<?> type) {
        return adapter.getBaseType( type );
    }

    @Override
    @SuppressWarnings("unchecked")
    public MorphProperty<P> getMorphProperty(final Class<?> type) {
        return adapter.getMorphProperty( type );
    }

    @Override
    public boolean canMorph(final String definitionId) {
        return adapter.canMorph( definitionId );
    }

    @Override
    public String getDefaultDefinition() {
        return adapter.getDefaultDefinition();
    }

    @Override
    @SuppressWarnings("unchecked")
    public Collection<String> getMorphTargets(final S source) {
        return adapter.getMorphTargets( source );
    }

    @Override
    @SuppressWarnings("unchecked")
    public T morph(final S source, final String target) {
        return (T) adapter.morph( source, target );
    }

    @Override
    public boolean accepts(final Class<?> type) {
        return adapter.accepts( type );
    }

}
