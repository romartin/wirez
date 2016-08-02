package org.wirez.backend;

import org.wirez.core.api.AbstractFactoryManager;
import org.wirez.core.api.DefinitionManager;
import org.wirez.core.factory.definition.DefinitionFactory;
import org.wirez.core.factory.graph.EdgeFactory;
import org.wirez.core.factory.graph.GraphFactory;
import org.wirez.core.factory.graph.NodeFactory;
import org.wirez.core.backend.annotation.Application;
import org.wirez.core.registry.RegistryFactory;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

@ApplicationScoped
@Application
public class ApplicationFactoryManager extends AbstractFactoryManager {

    private Instance<DefinitionFactory<?>> definitionFactoryInstances;
    private Instance<GraphFactory<?>> graphFactoryInstances;
    private Instance<NodeFactory<?>> nodeFactoryInstances;
    private Instance<EdgeFactory<?>> edgeFactoryInstances;

    protected ApplicationFactoryManager() {
        super();
    }

    @Inject
    public ApplicationFactoryManager( final RegistryFactory registryFactory,
                                      final DefinitionManager definitionManager,
                                      final Instance<DefinitionFactory<?>> definitionFactoryInstances,
                                      final Instance<GraphFactory<?>> graphFactoryInstances,
                                      final Instance<NodeFactory<?>> nodeFactoryInstances,
                                      final Instance<EdgeFactory<?>> edgeFactoryInstances ) {
        super( registryFactory, definitionManager );
        this.definitionFactoryInstances = definitionFactoryInstances;
        this.graphFactoryInstances = graphFactoryInstances;
        this.nodeFactoryInstances = nodeFactoryInstances;
        this.edgeFactoryInstances = edgeFactoryInstances;
    }

    @PostConstruct
    public void init() {
        initDefinitionFactories();
        initGraphFactories();
    }

    @SuppressWarnings( "unchecked" )
    private void initDefinitionFactories() {
        for ( DefinitionFactory<?> factory : definitionFactoryInstances ) {
            registry().register( factory );
        }
    }

    @SuppressWarnings( "unchecked" )
    private void initGraphFactories() {
        for ( GraphFactory<?> factory : graphFactoryInstances ) {
            registry().register( factory );
        }
        for ( NodeFactory<?> factory : nodeFactoryInstances ) {
            registry().register( factory );
        }
        for ( EdgeFactory<?> factory : edgeFactoryInstances ) {
            registry().register( factory );
        }
    }

}
