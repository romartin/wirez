package org.kie.workbench.common.stunner.core.rule.impl;

import org.kie.workbench.common.stunner.core.rule.*;

public abstract class AbstractCardinalityRuleManager<A, B> extends AbstractRuleManager<CardinalityRule> implements CardinalityRuleManager<A, B> {

    protected abstract RuleViolations doEvaluate( A labels, B candidatesCount, Operation operation );

    @Override
    public boolean supports( final Rule rule ) {
        return rule instanceof CardinalityRule;
    }

    @Override
    public RuleViolations evaluate( A labels, B candidatesCount, Operation operation ) {
        if ( rules.isEmpty() ) {
            return new DefaultRuleViolations();
        }
        return doEvaluate( labels, candidatesCount, operation );
    }
    
    
}
