package org.wirez.core.client.definition.adapter.binding;

import org.wirez.core.api.FactoryManager;
import org.wirez.core.definition.adapter.binding.AbstractBindableMorphAdapter;

class BindableMorphAdapterImpl<S, T> extends AbstractBindableMorphAdapter<S, T> {

    public BindableMorphAdapterImpl(final FactoryManager factoryManager) {
        super( factoryManager );
    }

    @Override
    protected T doMerge( final S source,
                         final T result ) {
        return ClientBindingUtils.merge( source, result );
    }

}
