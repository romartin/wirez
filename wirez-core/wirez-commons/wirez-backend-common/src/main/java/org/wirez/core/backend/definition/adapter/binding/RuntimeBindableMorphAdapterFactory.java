package org.wirez.core.backend.definition.adapter.binding;

import org.wirez.core.api.FactoryManager;
import org.wirez.core.definition.adapter.binding.BindableInheritanceMorphAdapter;
import org.wirez.core.definition.adapter.binding.BindableMorphAdapter;
import org.wirez.core.definition.adapter.binding.BindableMorphAdapterFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

@ApplicationScoped
public class RuntimeBindableMorphAdapterFactory implements BindableMorphAdapterFactory {

    FactoryManager factoryManager;

    protected RuntimeBindableMorphAdapterFactory() {
        this( null );
    }

    @Inject
    public RuntimeBindableMorphAdapterFactory(final FactoryManager factoryManager) {
        this.factoryManager = factoryManager;
    }

    @Produces
    public BindableMorphAdapter newBindableMorphAdapter() {
        return new RuntimeBindableMorphAdapter( factoryManager );
    }

    @Produces
    public BindableInheritanceMorphAdapter newBindableInheritanceMorphAdapter() {
        return new RuntimeBindableInheritanceMorphAdapter( factoryManager );
    }

}
