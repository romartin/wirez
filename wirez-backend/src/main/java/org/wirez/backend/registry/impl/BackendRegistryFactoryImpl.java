package org.wirez.backend.registry.impl;

import org.wirez.core.api.DefinitionManager;
import org.wirez.core.backend.registry.BackendRegistryFactory;
import org.wirez.core.diagram.Diagram;
import org.wirez.core.registry.diagram.DiagramRegistry;
import org.wirez.core.registry.impl.AbstractRegistryFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class BackendRegistryFactoryImpl extends AbstractRegistryFactory implements BackendRegistryFactory {

    protected BackendRegistryFactoryImpl() {
    }

    @Inject
    public BackendRegistryFactoryImpl( final DefinitionManager definitionManager ) {
        super( definitionManager );
    }

    @Override
    public <T extends Diagram> DiagramRegistry<T> newDiagramSynchronizedRegistry() {
        return new SyncDiagramListRegistry<T>();
    }
}
