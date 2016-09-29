package org.kie.workbench.common.stunner.core.client.registry.impl;

import org.kie.workbench.common.stunner.core.client.registry.ClientRegistryFactory;
import org.kie.workbench.common.stunner.core.definition.adapter.AdapterManager;
import org.kie.workbench.common.stunner.core.registry.impl.AbstractRegistryFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class ClientRegistryFactoryImpl extends AbstractRegistryFactory implements ClientRegistryFactory {

    protected ClientRegistryFactoryImpl() {
    }

    @Inject
    public ClientRegistryFactoryImpl( final AdapterManager adapterManager ) {
        super( adapterManager );
    }

}
