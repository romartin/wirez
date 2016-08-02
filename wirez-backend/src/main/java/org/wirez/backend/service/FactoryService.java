package org.wirez.backend.service;

import org.jboss.errai.bus.server.annotations.Service;
import org.wirez.backend.ApplicationFactoryManager;
import org.wirez.core.api.DefinitionManager;
import org.wirez.core.factory.definition.DefinitionFactory;
import org.wirez.core.factory.graph.EdgeFactory;
import org.wirez.core.factory.graph.GraphFactory;
import org.wirez.core.factory.graph.NodeFactory;
import org.wirez.core.registry.RegistryFactory;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;

@Service
public class FactoryService extends ApplicationFactoryManager implements org.wirez.core.remote.FactoryService {

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
