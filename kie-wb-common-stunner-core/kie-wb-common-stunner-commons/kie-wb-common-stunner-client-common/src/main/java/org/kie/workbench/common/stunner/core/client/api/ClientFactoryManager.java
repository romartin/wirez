package org.kie.workbench.common.stunner.core.client.api;

import org.jboss.errai.ioc.client.container.SyncBeanDef;
import org.jboss.errai.ioc.client.container.SyncBeanManager;
import org.kie.workbench.common.stunner.core.api.AbstractFactoryManager;
import org.kie.workbench.common.stunner.core.api.DefinitionManager;
import org.kie.workbench.common.stunner.core.factory.definition.DefinitionFactory;
import org.kie.workbench.common.stunner.core.factory.graph.EdgeFactory;
import org.kie.workbench.common.stunner.core.factory.graph.GraphFactory;
import org.kie.workbench.common.stunner.core.factory.graph.NodeFactory;
import org.kie.workbench.common.stunner.core.registry.RegistryFactory;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Collection;

@ApplicationScoped
public class ClientFactoryManager extends AbstractFactoryManager {

    private final SyncBeanManager beanManager;

    protected ClientFactoryManager() {
        super();
        this.beanManager = null;
    }

    @Inject
    public ClientFactoryManager( final RegistryFactory registryFactory,
                                   final DefinitionManager definitionManager,
                                 final SyncBeanManager beanManager ) {
        super( registryFactory, definitionManager );
        this.beanManager = beanManager;

    }

    @PostConstruct
    @SuppressWarnings("unchecked")
    public void init() {

        // Client definition factories..
        Collection<SyncBeanDef<DefinitionFactory>> beanDefSetAdapters = beanManager.lookupBeans(DefinitionFactory.class);
        for (SyncBeanDef<DefinitionFactory> defSet : beanDefSetAdapters) {
            DefinitionFactory factory = defSet.getInstance();
            registry().register( factory );
        }

        // Graph factories.
        Collection<SyncBeanDef<GraphFactory>> fAdapters = beanManager.lookupBeans(GraphFactory.class);
        for (SyncBeanDef<GraphFactory> defSet : fAdapters ) {
            GraphFactory factory = defSet.getInstance();
            registry().register( factory );
        }

        // Node factories.
        Collection<SyncBeanDef<NodeFactory>> nAdapters = beanManager.lookupBeans(NodeFactory.class);
        for (SyncBeanDef<NodeFactory> defSet : nAdapters ) {
            NodeFactory factory = defSet.getInstance();
            registry().register( factory );
        }

        // Edge factories.
        Collection<SyncBeanDef<EdgeFactory>> eAdapters = beanManager.lookupBeans(EdgeFactory.class);
        for (SyncBeanDef<EdgeFactory> defSet : eAdapters ) {
            EdgeFactory factory = defSet.getInstance();
            registry().register( factory );
        }

    }

}
