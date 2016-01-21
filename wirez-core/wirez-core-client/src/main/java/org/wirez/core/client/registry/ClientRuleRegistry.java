package org.wirez.core.client.registry;

import org.jboss.errai.ioc.client.container.IOCBeanDef;
import org.jboss.errai.ioc.client.container.SyncBeanManager;
import org.wirez.core.api.registry.SharedRuleRegistry;
import org.wirez.core.api.rule.Rule;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Collection;

@ApplicationScoped
public class ClientRuleRegistry extends SharedRuleRegistry {

    SyncBeanManager beanManager;

    @Inject
    public ClientRuleRegistry(SyncBeanManager beanManager) {
        this.beanManager = beanManager;
    }
    
    @PostConstruct
    public void init() {
        Collection<IOCBeanDef<Rule>> ruleBeanDefs = beanManager.lookupBeans(Rule.class);
        for (IOCBeanDef<Rule> ruleDef : ruleBeanDefs) {
            Rule definitionSet = ruleDef.getInstance();
        }
    }
    
}
