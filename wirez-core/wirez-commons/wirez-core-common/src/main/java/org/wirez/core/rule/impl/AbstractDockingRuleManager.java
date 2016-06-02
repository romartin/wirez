package org.wirez.core.rule.impl;

import org.wirez.core.rule.*;

public abstract class AbstractDockingRuleManager<A, B> extends AbstractRuleManager<DockingRule> implements DockingRuleManager<A, B> {

    protected abstract RuleViolations doEvaluate(A targetId, B candidateRoles );

    @Override
    public boolean supports( final Rule rule ) {
        return rule instanceof DockingRule;
    }

    @Override
    public RuleViolations evaluate( final A targetId, final B candidateRoles ) {
        if ( rules.isEmpty() ) {
            return new DefaultRuleViolations();
        }
        return doEvaluate( targetId, candidateRoles );
    }
    
    
}
