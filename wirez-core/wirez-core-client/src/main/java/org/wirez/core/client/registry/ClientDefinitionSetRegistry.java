package org.wirez.core.client.registry;

import org.jboss.errai.ioc.client.container.IOCBeanDef;
import org.jboss.errai.ioc.client.container.SyncBeanManager;
import org.wirez.core.api.definition.DefinitionSet;
import org.wirez.core.api.registry.BaseListRegistry;
import org.wirez.core.api.registry.DefinitionSetRegistry;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.Collection;

@ApplicationScoped
public class ClientDefinitionSetRegistry extends BaseListRegistry<DefinitionSet> implements DefinitionSetRegistry {

    SyncBeanManager beanManager;

    @Inject
    public ClientDefinitionSetRegistry(SyncBeanManager beanManager) {
        this.beanManager = beanManager;
    }

    @PostConstruct
    public void init() {
        initDefinitionSets();
    }

    private void initDefinitionSets() {
        
        // Definitions sets presents on client side. TODO: Not working - See ShowcaseEntryPoint@fixBpmn
        Collection<IOCBeanDef<DefinitionSet>> beanDefs = beanManager.lookupBeans(DefinitionSet.class);
        for (IOCBeanDef<DefinitionSet> defSet : beanDefs) {
            DefinitionSet definitionSet = defSet.getInstance();
            items.add(definitionSet);
        }
    }

    @Override
    protected String getItemId(final DefinitionSet item) {
        return item.getId();
    }
    
}
