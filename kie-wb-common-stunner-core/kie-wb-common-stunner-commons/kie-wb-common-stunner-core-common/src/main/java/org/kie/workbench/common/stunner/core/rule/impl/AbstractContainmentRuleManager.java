package org.kie.workbench.common.stunner.core.rule.impl;

import org.kie.workbench.common.stunner.core.rule.*;

public abstract class AbstractContainmentRuleManager<A, B> extends AbstractRuleManager<ContainmentRule> implements ContainmentRuleManager<A, B> {

    protected abstract RuleViolations doEvaluate( A targetId, B candidateRoles );

    @Override
    public boolean supports( final Rule rule ) {
        return rule instanceof ContainmentRule;
    }

    @Override
    public RuleViolations evaluate( final A targetId, final B candidateRoles ) {
        if ( rules.isEmpty() ) {
            return new DefaultRuleViolations();
        }
        return doEvaluate( targetId, candidateRoles );
    }
    
    
}
