package org.wirez.core.client.definition.adapter.binding;

import org.wirez.core.api.FactoryManager;
import org.wirez.core.definition.adapter.binding.BindableInheritanceMorphAdapter;
import org.wirez.core.definition.adapter.binding.BindableMorphAdapter;
import org.wirez.core.definition.adapter.binding.BindableMorphAdapterFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

@Dependent
public class BindableMorphAdapterFactoryImpl implements BindableMorphAdapterFactory {

    FactoryManager factoryManager;

    protected BindableMorphAdapterFactoryImpl() {
        this( null );
    }

    @Inject
    public BindableMorphAdapterFactoryImpl(final FactoryManager factoryManager) {
        this.factoryManager = factoryManager;
    }

    @Produces
    public BindableMorphAdapter newBindableMorphAdapter() {
        return new BindableMorphAdapterImpl( factoryManager );
    }

    @Produces
    public BindableInheritanceMorphAdapter newBindableInheritanceMorphAdapter() {
        return new BindableInheritanceMorphAdapterImpl( factoryManager );
    }

}
