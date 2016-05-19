package org.wirez.core.rule.impl.violations;

import org.wirez.core.rule.RuleViolation;

public abstract class AbstractRuleViolation implements RuleViolation {

    @Override
    public Type getViolationType() {
        return Type.ERROR;
    }
    
}
