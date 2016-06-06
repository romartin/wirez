package org.wirez.core.backend.definition.adapter.binding;

import org.wirez.core.api.FactoryManager;
import org.wirez.core.definition.adapter.binding.AbstractBindableInheritanceMorphAdapter;
import org.wirez.core.definition.adapter.binding.BindableInheritanceMorphAdapter;

class RuntimeBindableInheritanceMorphAdapter<S, T extends S, P> extends AbstractBindableInheritanceMorphAdapter<S, T, P>
        implements BindableInheritanceMorphAdapter<S, T, P> {

    RuntimeBindableInheritanceMorphAdapter(final FactoryManager factoryManager ) {
        super( factoryManager );
    }

    @Override
    protected T doMerge( final S source,
                         final T result ) {
        return RuntimeBindingUtils.merge( source, result );
    }

}
