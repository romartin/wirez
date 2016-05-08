package org.wirez.core.api.rule;

public interface ContainmentRuleManager<A, B> extends RuleManager<ContainmentRule> {
    
    RuleViolations evaluate( A targetId, B candidateRoles );
    
}
