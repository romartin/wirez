package org.wirez.core.api.rule;

public interface CardinalityRuleManager<A, B> extends RuleManager<CardinalityRule> {
    
    RuleViolations evaluate( A labels, B candidates, Operation operation );
    
}
