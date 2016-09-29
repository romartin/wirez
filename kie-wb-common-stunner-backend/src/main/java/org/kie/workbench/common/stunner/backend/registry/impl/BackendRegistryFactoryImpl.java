package org.kie.workbench.common.stunner.backend.registry.impl;

import org.kie.workbench.common.stunner.core.backend.registry.BackendRegistryFactory;
import org.kie.workbench.common.stunner.core.definition.adapter.AdapterManager;
import org.kie.workbench.common.stunner.core.diagram.Diagram;
import org.kie.workbench.common.stunner.core.registry.diagram.DiagramRegistry;
import org.kie.workbench.common.stunner.core.registry.impl.AbstractRegistryFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class BackendRegistryFactoryImpl extends AbstractRegistryFactory implements BackendRegistryFactory {

    protected BackendRegistryFactoryImpl() {
    }

    @Inject
    public BackendRegistryFactoryImpl( final AdapterManager adapterManager ) {
        super( adapterManager );
    }

    @Override
    public <T extends Diagram> DiagramRegistry<T> newDiagramSynchronizedRegistry() {
        return new SyncDiagramListRegistry<T>();
    }
}
