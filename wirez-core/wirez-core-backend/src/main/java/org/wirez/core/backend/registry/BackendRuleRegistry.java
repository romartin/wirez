package org.wirez.core.backend.registry;

import org.wirez.core.api.registry.SharedRuleRegistry;
import org.wirez.core.api.rule.Rule;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

@ApplicationScoped
public class BackendRuleRegistry extends SharedRuleRegistry {
    
    Instance<Rule> ruleInstances;

    @Inject
    public BackendRuleRegistry(Instance<Rule> ruleInstances) {
        this.ruleInstances = ruleInstances;
    }

    @PostConstruct
    public void init() {
        // Force rule construction.
        for (Rule rule : ruleInstances) {
            
        }
    }
    
}
