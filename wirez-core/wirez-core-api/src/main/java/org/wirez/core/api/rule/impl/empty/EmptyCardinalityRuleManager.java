package org.wirez.core.api.rule.impl.empty;

import org.wirez.core.api.rule.CardinalityRuleManager;
import org.wirez.core.api.rule.DefaultRuleViolations;
import org.wirez.core.api.rule.RuleViolations;
import org.wirez.core.api.rule.impl.AbstractCardinalityRuleManager;

import javax.enterprise.context.Dependent;

@Dependent
@Empty
public class EmptyCardinalityRuleManager extends AbstractCardinalityRuleManager<Object, Object> 
        implements CardinalityRuleManager<Object, Object> {

    public static final transient EmptyCardinalityRuleManager INSTANCE = new EmptyCardinalityRuleManager();

    public static final String NAME = "Empty Cardinality Rule Manager";
    
    @Override
    public String getName() {
        return NAME;
    }

    @Override
    protected RuleViolations doEvaluate(final Object labels, 
                                        final Object candidatesCount, 
                                        final Operation operation) {
        return new DefaultRuleViolations();
    }
    
}
