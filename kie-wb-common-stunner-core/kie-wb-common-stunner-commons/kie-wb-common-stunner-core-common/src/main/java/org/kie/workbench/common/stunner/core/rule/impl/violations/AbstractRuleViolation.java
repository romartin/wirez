package org.kie.workbench.common.stunner.core.rule.impl.violations;

import org.kie.workbench.common.stunner.core.rule.RuleViolation;

public abstract class AbstractRuleViolation implements RuleViolation {

    @Override
    public Type getViolationType() {
        return Type.ERROR;
    }
    
}
