package org.kie.workbench.common.stunner.backend.service;

import org.jboss.errai.bus.server.annotations.Service;
import org.kie.workbench.common.stunner.backend.ApplicationFactoryManager;
import org.kie.workbench.common.stunner.core.api.DefinitionManager;
import org.kie.workbench.common.stunner.core.factory.definition.DefinitionFactory;
import org.kie.workbench.common.stunner.core.factory.graph.EdgeFactory;
import org.kie.workbench.common.stunner.core.factory.graph.GraphFactory;
import org.kie.workbench.common.stunner.core.factory.graph.NodeFactory;
import org.kie.workbench.common.stunner.core.registry.RegistryFactory;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;

@Service
public class FactoryService extends ApplicationFactoryManager implements org.kie.workbench.common.stunner.core.remote.FactoryService {

    @Inject
    public FactoryService( final RegistryFactory registryFactory,
                           final DefinitionManager definitionManager,
                           final Instance<DefinitionFactory<?>> definitionFactoryInstances,
                           final Instance<GraphFactory<?>> graphFactoryInstances,
                           final Instance<NodeFactory<?>> nodeFactoryInstances,
                           final Instance<EdgeFactory<?>> edgeFactoryInstances ) {
        super( registryFactory, definitionManager, definitionFactoryInstances,
                graphFactoryInstances, nodeFactoryInstances, edgeFactoryInstances );
    }

}
