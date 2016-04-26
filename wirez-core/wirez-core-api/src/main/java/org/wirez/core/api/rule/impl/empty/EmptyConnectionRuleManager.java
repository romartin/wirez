package org.wirez.core.api.rule.impl.empty;

import org.wirez.core.api.rule.ConnectionRuleManager;
import org.wirez.core.api.rule.DefaultRuleViolations;
import org.wirez.core.api.rule.RuleViolations;
import org.wirez.core.api.rule.impl.AbstractConnectionRuleManager;

import javax.enterprise.context.Dependent;

@Dependent
@Empty
public class EmptyConnectionRuleManager extends AbstractConnectionRuleManager<Object, Object> 
        implements ConnectionRuleManager<Object, Object> {

    public static final transient EmptyConnectionRuleManager INSTANCE = new EmptyConnectionRuleManager();
    
    private static final String NAME = "Empty Connection Rule Manager";
    
    @Override
    public String getName() {
        return NAME;
    }


    @Override
    protected RuleViolations doEvaluate(final Object edgeId, 
                                        final Object outgoingLabels, 
                                        final Object incomingLabels) {
        return new DefaultRuleViolations();
    }
    
}
