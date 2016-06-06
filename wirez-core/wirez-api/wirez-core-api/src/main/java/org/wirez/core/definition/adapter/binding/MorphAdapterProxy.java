package org.wirez.core.definition.adapter.binding;

import org.wirez.core.definition.adapter.MorphAdapter;

import java.util.Collection;
import java.util.Map;

public abstract class MorphAdapterProxy<S, T> implements MorphAdapter<S, T> {

    protected final BindableMorphAdapter adapter;

    public MorphAdapterProxy(final BindableMorphAdapterFactory morphAdapterProducer) {
        this.adapter = morphAdapterProducer.newBindableMorphAdapter();
        init();
    }

    @SuppressWarnings("unchecked")
    protected void init() {
        adapter.setDefaultDefinitionType( getDefaultDefinitionType() );
        adapter.setDomainMorphs( getDomainMorphs() );
    }

    protected abstract Class<?> getDefaultDefinitionType();

    protected abstract Map<Class<?>, Collection<Class<?>>> getDomainMorphs();

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
