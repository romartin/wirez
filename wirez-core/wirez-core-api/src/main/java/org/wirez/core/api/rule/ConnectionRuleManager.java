package org.wirez.core.api.rule;

public interface ConnectionRuleManager<A, B> extends RuleManager<ConnectionRule> {
    
    RuleViolations evaluate( A edgeId, B outgoingLabels, B incomingLabels );
    
}
