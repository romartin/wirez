package org.kie.workbench.common.stunner.core.rule.impl;

import org.kie.workbench.common.stunner.core.rule.Rule;
import org.kie.workbench.common.stunner.core.rule.RuleManager;

public abstract class AbstractWrappedRuleManager<R extends Rule, M extends RuleManager<R>> implements RuleManager<R> {

    protected abstract M getWrapped();
   
    @Override
    public boolean supports(final Rule rule) {
        return getWrapped().supports( rule );
    }

    @Override
    public RuleManager addRule(final R rule) {
        return getWrapped().addRule( rule );
    }

    @Override
    public RuleManager clearRules() {
        getWrapped().clearRules();
        return this;
    }
    
}
