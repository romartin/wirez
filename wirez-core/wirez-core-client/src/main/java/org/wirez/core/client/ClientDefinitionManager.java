package org.wirez.core.client;

import org.jboss.errai.ioc.client.container.IOC;
import org.jboss.errai.ioc.client.container.SyncBeanDef;
import org.jboss.errai.ioc.client.container.SyncBeanManager;
import org.wirez.core.api.BaseDefinitionManager;
import org.wirez.core.api.definition.DefinitionSet;
import org.wirez.core.api.definition.adapter.*;
import org.wirez.core.api.registry.DiagramRegistry;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Collection;

@ApplicationScoped
public class ClientDefinitionManager extends BaseDefinitionManager {
    
    SyncBeanManager beanManager;

    // Remove later...
    public static ClientDefinitionManager get() {
        Collection<SyncBeanDef<ClientDefinitionManager>> beans = IOC.getBeanManager().lookupBeans(ClientDefinitionManager.class);
        SyncBeanDef<ClientDefinitionManager> beanDef = beans.iterator().next();
        return beanDef.getInstance();
    }

    public ClientDefinitionManager() {
    }

    @Inject
    public ClientDefinitionManager(final DiagramRegistry diagramRegistry,
                                   final SyncBeanManager beanManager) {
        super(diagramRegistry);
        this.beanManager = beanManager;
    }
    
    @PostConstruct
    public void init() {

        // Definition Sets.
        Collection<SyncBeanDef<DefinitionSet>> beanDefSets = beanManager.lookupBeans(DefinitionSet.class);
        for (SyncBeanDef<DefinitionSet> defSet : beanDefSets) {
            DefinitionSet definitionSet = defSet.getInstance();
            definitionSets.add(definitionSet);
        }
        
        // DefinitionSet client adapters.
        Collection<SyncBeanDef<DefinitionSetAdapter>> beanDefSetAdapters = beanManager.lookupBeans(DefinitionSetAdapter.class);
        for (SyncBeanDef<DefinitionSetAdapter> defSet : beanDefSetAdapters) {
            DefinitionSetAdapter definitionSet = defSet.getInstance();
            definitionSetAdapters.add(definitionSet);
        }
        sortAdapters(definitionAdapters);

        // DefinitionSetRule client adapters.
        Collection<SyncBeanDef<DefinitionSetRuleAdapter>> beanDefSetRuleAdapters = beanManager.lookupBeans(DefinitionSetRuleAdapter.class);
        for (SyncBeanDef<DefinitionSetRuleAdapter> defSet : beanDefSetRuleAdapters) {
            DefinitionSetRuleAdapter definitionSet = defSet.getInstance();
            definitionSetRuleAdapters.add(definitionSet);
        }
        sortAdapters(definitionSetRuleAdapters);

        // Definition client adapters.
        Collection<SyncBeanDef<DefinitionAdapter>> beanDefAdapters = beanManager.lookupBeans(DefinitionAdapter.class);
        for (SyncBeanDef<DefinitionAdapter> defSet : beanDefAdapters) {
            DefinitionAdapter definitionSet = defSet.getInstance();
            definitionAdapters.add(definitionSet);
        }
        sortAdapters(definitionAdapters);
        
        // PropertySet client adapters.
        Collection<SyncBeanDef<PropertySetAdapter>> beanPropSetAdapters = beanManager.lookupBeans(PropertySetAdapter.class);
        for (SyncBeanDef<PropertySetAdapter> defSet : beanPropSetAdapters) {
            PropertySetAdapter definitionSet = defSet.getInstance();
            propertySetAdapters.add(definitionSet);
        }
        sortAdapters(propertySetAdapters);
        
        // Property client adapters.
        Collection<SyncBeanDef<PropertyAdapter>> beanPropAdapters = beanManager.lookupBeans(PropertyAdapter.class);
        for (SyncBeanDef<PropertyAdapter> defSet : beanPropAdapters) {
            PropertyAdapter definitionSet = defSet.getInstance();
            propertyAdapters.add(definitionSet);
        }
        sortAdapters(propertyAdapters);
        
    }

}
