package org.kie.workbench.common.stunner.core.rule.impl;

import org.kie.workbench.common.stunner.core.rule.Rule;
import org.kie.workbench.common.stunner.core.rule.RuleManager;

import java.util.LinkedHashSet;
import java.util.Set;

public abstract class AbstractRuleManager<R extends Rule> implements RuleManager<R> {

    protected final Set<R> rules = new LinkedHashSet<R>();
    
    @Override
    public RuleManager addRule(final R rule) {
        
        if ( supports( rule ) ) {
            rules.add( rule );
        }
        
        return this;
    }

    @Override
    public RuleManager clearRules() {
        rules.clear();
        return this;
    }
    
}
