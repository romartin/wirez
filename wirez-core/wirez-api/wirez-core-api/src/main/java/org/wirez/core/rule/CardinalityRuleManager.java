package org.wirez.core.rule;

public interface CardinalityRuleManager<A, B> extends RuleManager<CardinalityRule> {
    
    RuleViolations evaluate( A labels, B candidates, Operation operation );
    
}
