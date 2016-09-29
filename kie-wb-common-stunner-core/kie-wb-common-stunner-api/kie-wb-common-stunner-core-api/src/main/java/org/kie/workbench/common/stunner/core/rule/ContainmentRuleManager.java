package org.kie.workbench.common.stunner.core.rule;

public interface ContainmentRuleManager<A, B> extends RuleManager<ContainmentRule> {
    
    RuleViolations evaluate( A targetId, B candidateRoles );
    
}
