package org.wirez.core.rule;

public interface ConnectionRuleManager<A, B> extends RuleManager<ConnectionRule> {
    
    RuleViolations evaluate( A edgeId, B outgoingLabels, B incomingLabels );
    
}
