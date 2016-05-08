package org.wirez.core.api.rule.impl;

import org.wirez.core.api.rule.*;

public abstract class AbstractConnectionRuleManager<A, B> extends AbstractRuleManager<ConnectionRule> implements ConnectionRuleManager<A, B> {

    protected abstract RuleViolations doEvaluate( A edgeId, B outgoingLabels, B incomingLabels );

    @Override
    public boolean supports( final Rule rule ) {
        return rule instanceof ConnectionRule;
    }

    @Override
    public RuleViolations evaluate( A edgeId, B outgoingLabels, B incomingLabels ) {
        if ( rules.isEmpty() ) {
            return new DefaultRuleViolations();
        }
        return doEvaluate( edgeId, outgoingLabels, incomingLabels );
    }
    
    
}
