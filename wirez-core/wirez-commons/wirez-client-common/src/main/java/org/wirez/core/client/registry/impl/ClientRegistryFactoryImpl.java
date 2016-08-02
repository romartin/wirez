package org.wirez.core.client.registry.impl;

import org.wirez.core.api.DefinitionManager;
import org.wirez.core.client.registry.ClientRegistryFactory;
import org.wirez.core.registry.impl.AbstractRegistryFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class ClientRegistryFactoryImpl extends AbstractRegistryFactory implements ClientRegistryFactory {

    protected ClientRegistryFactoryImpl() {
    }

    @Inject
    public ClientRegistryFactoryImpl( final DefinitionManager definitionManager ) {
        super( definitionManager );
    }

}
