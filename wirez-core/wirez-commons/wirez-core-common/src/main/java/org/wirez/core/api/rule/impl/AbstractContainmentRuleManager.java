package org.wirez.core.api.rule.impl;

import org.wirez.core.api.rule.*;

public abstract class AbstractContainmentRuleManager<A, B> extends AbstractRuleManager<ContainmentRule> implements ContainmentRuleManager<A, B> {

    protected abstract RuleViolations dpEvaluate( A targetId, B candidateRoles );

    @Override
    public boolean supports( final Rule rule ) {
        return rule instanceof ContainmentRule;
    }

    @Override
    public RuleViolations evaluate( final A targetId, final B candidateRoles ) {
        if ( rules.isEmpty() ) {
            return new DefaultRuleViolations();
        }
        return dpEvaluate( targetId, candidateRoles );
    }
    
    
}
