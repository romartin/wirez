package org.kie.workbench.common.stunner.core.client.api;

import org.jboss.errai.ioc.client.container.SyncBeanDef;
import org.jboss.errai.ioc.client.container.SyncBeanManager;
import org.kie.workbench.common.stunner.core.definition.adapter.*;
import org.kie.workbench.common.stunner.core.api.AbstractDefinitionManager;
import org.kie.workbench.common.stunner.core.definition.DefinitionSetProxy;
import org.kie.workbench.common.stunner.core.registry.RegistryFactory;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Collection;

@ApplicationScoped
public class ClientDefinitionManager extends AbstractDefinitionManager {
    
    private final SyncBeanManager beanManager;

    protected ClientDefinitionManager() {
        super();
        this.beanManager = null;
    }

    @Inject
    public ClientDefinitionManager( final SyncBeanManager beanManager,
                                    final RegistryFactory registryFactory,
                                    final AdapterManager adapterManager ) {
        super( registryFactory, adapterManager );
        this.beanManager = beanManager;
    }

    @PostConstruct
    @SuppressWarnings("unchecked")
    public void init() {

        // DefinitionSet client adapters.
        Collection<SyncBeanDef<DefinitionSetAdapter>> beanDefSetAdapters = beanManager.lookupBeans(DefinitionSetAdapter.class);
        for (SyncBeanDef<DefinitionSetAdapter> defSet : beanDefSetAdapters) {
            DefinitionSetAdapter definitionSet = defSet.getInstance();
            addAdapter( definitionSet );
        }

        // DefinitionSetRule client adapters.
        Collection<SyncBeanDef<DefinitionSetRuleAdapter>> beanDefSetRuleAdapters = beanManager.lookupBeans(DefinitionSetRuleAdapter.class);
        for (SyncBeanDef<DefinitionSetRuleAdapter> defSet : beanDefSetRuleAdapters) {
            DefinitionSetRuleAdapter definitionSet = defSet.getInstance();
            addAdapter(definitionSet);
        }

        // Definition client adapters.
        Collection<SyncBeanDef<DefinitionAdapter>> beanDefAdapters = beanManager.lookupBeans(DefinitionAdapter.class);
        for (SyncBeanDef<DefinitionAdapter> defSet : beanDefAdapters) {
            DefinitionAdapter definitionSet = defSet.getInstance();
            addAdapter(definitionSet);
        }

        // PropertySet client adapters.
        Collection<SyncBeanDef<PropertySetAdapter>> beanPropSetAdapters = beanManager.lookupBeans(PropertySetAdapter.class);
        for (SyncBeanDef<PropertySetAdapter> defSet : beanPropSetAdapters) {
            PropertySetAdapter definitionSet = defSet.getInstance();
            addAdapter(definitionSet);
        }

        // Property client adapters.
        Collection<SyncBeanDef<PropertyAdapter>> beanPropAdapters = beanManager.lookupBeans(PropertyAdapter.class);
        for (SyncBeanDef<PropertyAdapter> defSet : beanPropAdapters) {
            PropertyAdapter definitionSet = defSet.getInstance();
            addAdapter(definitionSet);
        }

        // Morph adapters.
        Collection<SyncBeanDef<MorphAdapter>> beanMorphAdapters = beanManager.lookupBeans( MorphAdapter.class );
        for ( SyncBeanDef<MorphAdapter> morphAdapter : beanMorphAdapters ) {
            MorphAdapter instance = morphAdapter.getInstance();
            addAdapter( instance );
        }

        // Once adapters present, add the Definition Sets found on current context.
        Collection<SyncBeanDef<DefinitionSetProxy>> beanDefSets = beanManager.lookupBeans(DefinitionSetProxy.class);
        for (SyncBeanDef<DefinitionSetProxy> defSet : beanDefSets) {
            DefinitionSetProxy definitionSetProxy = defSet.getInstance();
            Object definitionSet = definitionSetProxy.getDefinitionSet();
            addDefinitionSet( definitionSet );
        }

    }

}
