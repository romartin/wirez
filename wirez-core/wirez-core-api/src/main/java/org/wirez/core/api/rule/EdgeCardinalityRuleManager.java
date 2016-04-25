package org.wirez.core.api.rule;

public interface EdgeCardinalityRuleManager<A, B, C> extends RuleManager<EdgeCardinalityRule> {
    
    RuleViolations evaluate( A edgeId, B outLabels, B inLabels, C outEdgesCount, C inEdgesCount, Operation operation );
    
}
