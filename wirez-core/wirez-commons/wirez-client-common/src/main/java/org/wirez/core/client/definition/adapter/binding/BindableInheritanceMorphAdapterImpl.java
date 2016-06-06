package org.wirez.core.client.definition.adapter.binding;

import org.wirez.core.api.FactoryManager;
import org.wirez.core.definition.adapter.binding.AbstractBindableInheritanceMorphAdapter;
import org.wirez.core.definition.adapter.binding.BindableInheritanceMorphAdapter;

class BindableInheritanceMorphAdapterImpl<S, T extends S, P> extends AbstractBindableInheritanceMorphAdapter<S, T, P>
        implements BindableInheritanceMorphAdapter<S, T, P> {

    public BindableInheritanceMorphAdapterImpl( final FactoryManager factoryManager ) {
        super( factoryManager );
    }

    @Override
    protected T doMerge( final S source,
                         final T result ) {
        return ClientBindingUtils.merge( source, result );
    }

}
