package org.wirez.core.api.rule.impl.violations;

import org.wirez.core.api.rule.RuleViolation;

public abstract class AbstractRuleViolation implements RuleViolation {

    @Override
    public Type getViolationType() {
        return Type.ERROR;
    }
    
}
