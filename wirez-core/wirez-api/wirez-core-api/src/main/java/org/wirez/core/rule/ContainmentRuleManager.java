package org.wirez.core.rule;

public interface ContainmentRuleManager<A, B> extends RuleManager<ContainmentRule> {
    
    RuleViolations evaluate( A targetId, B candidateRoles );
    
}
