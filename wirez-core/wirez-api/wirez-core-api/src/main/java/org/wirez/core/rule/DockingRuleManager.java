package org.wirez.core.rule;

public interface DockingRuleManager<A, B> extends RuleManager<DockingRule> {
    
    RuleViolations evaluate(A targetId, B candidateRoles);
    
}
