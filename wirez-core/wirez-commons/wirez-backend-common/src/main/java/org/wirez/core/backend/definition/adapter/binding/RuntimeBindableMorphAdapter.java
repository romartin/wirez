package org.wirez.core.backend.definition.adapter.binding;

import org.wirez.core.api.FactoryManager;
import org.wirez.core.definition.adapter.binding.AbstractBindableMorphAdapter;

class RuntimeBindableMorphAdapter<S, T> extends AbstractBindableMorphAdapter<S, T> {

    RuntimeBindableMorphAdapter(final FactoryManager factoryManager) {
        super( factoryManager );
    }

    @Override
    protected T doMerge( final S source,
                         final T result ) {
        return RuntimeBindingUtils.merge( source, result );
    }

}
