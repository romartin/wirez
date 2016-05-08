package org.wirez.core.api.rule.impl;

import org.wirez.core.api.rule.*;

public abstract class AbstractEdgeCardinalityRuleManager<A, B, C> extends AbstractRuleManager<EdgeCardinalityRule> 
        implements EdgeCardinalityRuleManager<A, B, C> {

    protected abstract RuleViolations doEvaluate( A edgeId, B outLabels, B inLabels, C outEdgesCount, C inEdgesCount, Operation operation );

    @Override
    public boolean supports( final Rule rule ) {
        return rule instanceof EdgeCardinalityRule;
    }

    @Override
    public RuleViolations evaluate( A edgeId, B outLabels, B inLabels, C outEdgesCount, C inEdgesCount, Operation operation ) {
        if ( rules.isEmpty() ) {
            return new DefaultRuleViolations();
        }
        return doEvaluate( edgeId, outLabels, inLabels, outEdgesCount, inEdgesCount, operation );
    }
    
    
}
