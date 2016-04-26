package org.wirez.core.api.rule.impl.empty;

import org.wirez.core.api.rule.ContainmentRuleManager;
import org.wirez.core.api.rule.DefaultRuleViolations;
import org.wirez.core.api.rule.RuleViolations;
import org.wirez.core.api.rule.impl.AbstractContainmentRuleManager;

import javax.enterprise.context.Dependent;

@Dependent
@Empty
public class EmptyContainmentRuleManager extends AbstractContainmentRuleManager<Object, Object> 
        implements ContainmentRuleManager<Object, Object> {

    public static final transient EmptyContainmentRuleManager INSTANCE = new EmptyContainmentRuleManager();
    
    private static final String NAME = "Empty Containment Rule Manager";
    
    @Override
    public String getName() {
        return NAME;
    }


    @Override
    protected RuleViolations dpEvaluate(final Object targetId, 
                                        final Object candidateRoles) {
        return new DefaultRuleViolations();
    }
    
}
